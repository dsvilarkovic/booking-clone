from django.shortcuts import render, get_object_or_404, redirect
from django.conf import settings
from .models import AccommodationUnit, Day, Guest, Message, Reservation, AccommodationType, AccommodationCategory, Accommodation, Location, AdditionalService, AccommodationImage
from datetime import date, timedelta, datetime
from zeep import Client, Settings, helpers
from zeep.plugins import HistoryPlugin
from lxml import etree
import pdb
import base64
from django.core.files.base import ContentFile

def view_index(request):
    return render(request, 'booking/view_index.html')


def view_prices(request):
    units = AccommodationUnit.objects.all()
    if request.method == 'GET':
        context = {'units': units}
        return render(request, 'booking/view_prices.html', context)
    if request.method == 'POST':
        seldate = request.POST.get('date')
        selunit = request.POST.get('accomm_unit')
        querry = Day.objects.filter(unit=selunit, date=seldate)
        selunit = AccommodationUnit.objects.get(pk=selunit)
        if querry.exists():
            dateprice = querry.get().price
        else:
            dateprice = units.get(pk=request.POST.get(
                'accomm_unit')).default_price
        context = {'units': units, 'dateprice': dateprice,
                   'seldate': seldate, 'selunit': selunit}
        return render(request, 'booking/view_prices.html', context)


def edit_prices(request):
    if request.method == 'GET':
        units = AccommodationUnit.objects.all()
        context = {'units': units}
        return render(request, 'booking/edit_prices.html', context)
    if request.method == 'POST':
        unit = get_object_or_404(
            AccommodationUnit, pk=request.POST.get('accomm_unit'))
        start_date = datetime.strptime(
            request.POST.get('start_date'), "%Y-%m-%d").date()
        end_date = datetime.strptime(
            request.POST.get('end_date'), "%Y-%m-%d").date()

        delta = end_date - start_date
        for i in range(delta.days + 1):
            idate = start_date + timedelta(days=i)
            querry = Day.objects.filter(unit=unit.id, date=idate)
            if querry.exists():
                iday = querry.get()
            else:
                iday = Day()
                iday.unit = unit
                iday.date = idate
            iday.price = request.POST.get('price')
            iday.available = True if request.POST.get('available') else False
            iday.save()
        #backend comms    
        history = HistoryPlugin()
        client_settings = Settings(strict=False, xml_huge_tree=True)
        client = Client(settings.WSDL_ADDRESS_ACCOMMODATION, settings=client_settings, plugins=[history])

        transfer = unit.to_dict()
        transfer = {'AccommodationUnit': transfer}
        response = client.service.updateAccommodationUnit(**transfer)

        unit.day_set.all().delete()
        for elem in response['_value_1']:
            day_dict = helpers.serialize_object(elem['Day'])
            day = Day()
            day.unit = unit
            day.date = date.fromtimestamp(day_dict['date'] / 1000)
            day.price = day_dict['price']
            day.available = day_dict['available']
            day.save()

        return redirect('booking:view_prices')


def create_guest_list(reslist):
    guest_list = []
    for res in reslist:
        guest_name = str(res.guest)
        unit_name = str(res.unit)
        val = {
            'guest_name': guest_name,
            'unit_name': unit_name,
            'reservation_id': res.id
        }
        guest_list.append(val)
    return guest_list


def view_messages(request):
    resers = Reservation.objects.all()
    context = {'guest_list': create_guest_list(resers)}
    return render(request, 'booking/view_messages.html', context)


def messaging(request, reservation_id):
    if request.method == 'GET':
        resers = Reservation.objects.all()
        msgs = Message.objects.filter(reservation=reservation_id)
        curr_id = reservation_id
        context = {
            'guest_list': create_guest_list(resers),
            'message_list': msgs,
            'curr_id': curr_id
        }
        return render(request, 'booking/view_messages.html', context)
    if request.method == 'POST':
        msg = Message()
        msg.reservation = Reservation.objects.get(pk=reservation_id)
        msg.text = request.POST.get('msg_text')
        msg.mine = True
        msg.timestamp = datetime.now()
        msg.save()
        # TODO: backend comms here
        return redirect('booking:messaging', reservation_id=reservation_id)


def sync_all_data(request):
    history = HistoryPlugin()
    client_settings = Settings(strict=False, xml_huge_tree=True)
    acc_client = Client(settings.WSDL_ADDRESS_ACCOMMODATION, settings=client_settings, plugins=[history])
    auth_client = Client(settings.WSDL_ADDRESS_AUTHENTICATION, settings=client_settings)
    res_client = Client(settings.WSDL_ADDRESS_RESERVATION, settings=client_settings, plugins=[history])

    #TODO: izmeniti username i password
    token = auth_client.service.login(username='boris', password='boris')
    acc_client.transport.session.headers.update({'Authorization': token})
    res_client.transport.session.headers.update({'Authorization': token})
    # Accommodation Types
    AccommodationType.objects.all().delete()

    soap_response = acc_client.service.getAccommodationTypes()
    for atdict in soap_response:
        tmp = helpers.serialize_object(atdict['AccommodationType'])
        new_atype = AccommodationType(**tmp)
        new_atype.save()


    # Accommodation Categories
    AccommodationCategory.objects.all().delete()
    
    soap_response = acc_client.service.getAccommodationCategories()
    for acdict in soap_response:
        tmp = helpers.serialize_object(acdict['AccommodationCategory'])
        new_acat = AccommodationCategory(**tmp)
        new_acat.save()


    # Additional Services
    AdditionalService.objects.all().delete()

    soap_response = acc_client.service.getAdditionalServices()
    for addser in soap_response:
        tmp = helpers.serialize_object(addser['AdditionalService'])
        new_addser = AdditionalService(**tmp)
        new_addser.save()

    # Accommodations
    Accommodation.objects.all().delete()
    Location.objects.all().delete()
    try:
        soap_response = acc_client.service.getAccommodations()
    except:
        for hist in [history.last_sent, history.last_received]:
            print(etree.tostring(hist["envelope"], encoding="unicode", pretty_print=True))
    for adict in soap_response:
        tmp = helpers.serialize_object(adict['Accommodation'])
        aid = tmp['id']
        atype = tmp['AccommodationType']
        acat = tmp['AccommodationCategory']
        loc = tmp['Location']
        name = tmp['name']
        desc = tmp['description']
        services = tmp['AdditionalService']
        units = tmp['AccommodationUnit']
        
        # Create location
        new_location = Location()
        new_location.id = loc['id']
        new_location.address = loc['address']
        new_location.city = loc['city']
        new_location.longitude = loc['longitude']
        new_location.latitude = loc['latitude']
        new_location.save()

        new_acom = Accommodation()
        new_acom.id = aid
        new_acom.accommodation_type = AccommodationType.objects.get(pk=atype['id'])
        new_acom.category = AccommodationCategory.objects.get(pk=acat['id'])        

        new_acom.location = new_location
        new_acom.name = name
        new_acom.description = desc
        new_acom.save()

        #services
        for service in services:
            new_acom.services.add(AdditionalService.objects.get(pk=service['id']))

        #units
        for unit in units:
            uid = unit['id']
            name = unit['name']
            capacity = unit['capacity']
            default_price = unit['default_price']
            cancelation_period = unit['cancelation_period']
            days = unit['Day']

            new_unit = AccommodationUnit()
            new_unit.id = uid
            new_unit.name = name
            new_unit.capacity = capacity
            new_unit.default_price = default_price
            new_unit.cancelation_period = cancelation_period
            new_unit.accommodation = new_acom

            new_unit.save()

            # days
            for day in days:
                did = day['id']
                day_date = day['date']
                price = day['price']
                available = day['available']

                new_day = Day()
                new_day.id = did
                new_day.date = date.fromtimestamp(day_date / 1000)
                new_day.price = price
                new_day.available = available
                new_day.unit = new_unit

                new_day.save()

        # Images
        try:
            soap_response = acc_client.service.getAccommodationImages(aid)
        except:
            for hist in [history.last_sent, history.last_received]:
                print(etree.tostring(hist["envelope"], encoding="unicode", pretty_print=True))
            continue
        for image_dict in soap_response:
            image_dict = image_dict['Image']

            new_image = AccommodationImage()
            new_image.id = image_dict['id']
            image_name = 'acom_#' + str(new_acom.id) + '_' + str(new_image.id) + '.jgp'
            new_image.image = ContentFile(base64.b64decode(image_dict['value']), name=image_name)
            new_image.accommodation = new_acom
            new_image.save()


    # Reservations
    soap_response = res_client.service.getReservationList()
    for rdict in soap_response:
        rdict = rdict['Reservation']
        rid = rdict['id']
        beginning_date = date.fromtimestamp(rdict['beginning_date'] / 1000)
        end_date = date.fromtimestamp(rdict['end_date'] / 1000)
        final_price = rdict['finalPrice']
        checked_in = rdict['checked_in']

        # Guest
        guest_dict = rdict['User']
        new_guest = Guest()
        new_guest.id = guest_dict['id']
        new_guest.first_name = guest_dict['first_name']
        new_guest.last_name = guest_dict['last_name']
        new_guest.email = guest_dict['email']
        new_guest.save()

        aid = rdict['AccommodationUnit']['id']
        aunit = AccommodationUnit.objects.filter(id=aid).get()

        new_res = Reservation()
        new_res.id = rid
        new_res.beginning = beginning_date
        new_res.end = end_date
        new_res.final_price = final_price
        new_res.checked_in = checked_in
        new_res.unit = aunit
        new_res.guest = new_guest
        new_res.save()

    return render(request, 'booking/view_index.html')
