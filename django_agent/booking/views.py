from django.shortcuts import render, get_object_or_404, redirect
from django.conf import settings
from .models import AccommodationUnit, Day, Guest, Message, Reservation, AccommodationType, AccommodationCategory, Accommodation, Location, AdditionalService, AccommodationImage
from datetime import date, timedelta, datetime
from zeep import Client, Settings, helpers, xsd
from zeep.plugins import HistoryPlugin
from zeep.transports import Transport
from lxml import etree
import pdb
import base64
from django.core.files.base import ContentFile
from django.contrib import messages
import time


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
    client_settings = Settings(strict=False)
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

        available = True if request.POST.get('available') else False

        # check for reservation overlaps if unavailable
        if not available:
            check1 = Reservation.objects.filter(
                unit__exact=unit.id, beginning__lte=start_date, end__gte=start_date)
            check2 = Reservation.objects.filter(
                unit__exact=unit.id, beginning__lte=end_date, end__gte=end_date)
            if check1.exists() or check2.exists():
                message_text = 'Cannot set as unavailable because there are active reservations in this period.'
                messages.add_message(request, messages.ERROR, message_text)
                units = AccommodationUnit.objects.all()
                context = {'units': units}
                return render(request, 'booking/edit_prices.html', context)

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
            iday.available = available
            iday.save()
        # backend comms
        history = HistoryPlugin()
        transport = Transport()
        transport.session.headers.update({'Authorization': request.user.profile.token})
        client = Client(settings.WSDL_ADDRESS_ACCOMMODATION, transport=transport, plugins=[history], settings=client_settings)
        accommodation_service = client.create_service(
            '{http://www.ftn.uns.ac.rs/tim1/accommodationsoap}AccommodationSoapPortSoap11',
            'http://40.87.122.201:8762/api/accommodation/ws/'
        )

        transfer = unit.to_dict()
        transfer = {'AccommodationUnit': transfer}
        response = accommodation_service.updateAccommodationUnit(**transfer)

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
    client_settings = Settings(strict=False)
    if request.method == 'GET':
        transport = Transport()
        transport.session.headers.update({'Authorization': request.user.profile.token})
        client = Client(settings.WSDL_ADDRESS_RESERVATION, transport=transport, settings=client_settings)
        reservation_service = client.create_service(
            '{http://www.ftn.uns.ac.rs/tim1/reservationsoap}ReservationSoapPortSoap11',
            'http://40.87.122.201:8762/api/reservationsoap/ws/'
        )
        response = reservation_service.getMessages(reservation_id=reservation_id)

        if response:
            Message.objects.filter(reservation_id=reservation_id).delete()
            for message_dict in response:
                msg = Message()
                msg.reservation_id = reservation_id
                msg.text = message_dict['value']
                sender_email = message_dict['User']['email']
                #msg.mine = True if sender_email == request.user.email else False
                msg.mine = True if sender_email == 'boris' else False
                msg.timestamp = datetime.fromtimestamp(
                    message_dict['date'] / 1000)
                msg.save()

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

        transfer = dict()
        transfer['value'] = msg.text
        transfer['date'] = int(time.mktime(msg.timestamp.timetuple())) * 1000
        transfer['User'] = dict()
        transfer['User']['email'] = request.user.email
        transfer = {'Message': transfer}
        transfer['reservation_id'] = reservation_id

        transport = Transport()
        transport.session.headers.update({'Authorization': request.user.profile.token})
        client = Client(settings.WSDL_ADDRESS_MESSAGING, transport=transport, settings=client_settings)
        messaging_service = client.create_service(
            '{http://www.ftn.uns.ac.rs/tim1/messagingsoap}MessagingSoapPortSoap11',
            'http://40.87.122.201:8762/api/messagingsoap/ws/'
        )
        response = messaging_service.createMessage(**transfer)

        msg.id = response['message_id']
        msg.timestamp = datetime.fromtimestamp(response['timestamp'] / 1000)
        msg.save()
        return redirect('booking:messaging', reservation_id=reservation_id)


def sync_all_data(request):
    history = HistoryPlugin()
    client_settings = Settings(strict=False)
    auth_client = Client(settings.WSDL_ADDRESS_AUTHENTICATION)

    login_service = auth_client.create_service(
        '{http://www.ftn.uns.ac.rs/tim1/loginsoap}LoginSoapPortSoap11',
        'http://40.87.122.201:8762/api/loginsoap/loginsoap/'
    )

    username = request.user.email
    password = request.user.profile.password_cheat
    token = login_service.login(username=username, password=password)
    transport = Transport()
    transport.session.headers.update({'Authorization': token})
    acc_client = Client(settings.WSDL_ADDRESS_ACCOMMODATION, plugins=[history], transport=transport, settings=client_settings)
    res_client = Client(settings.WSDL_ADDRESS_RESERVATION, plugins=[history], transport=transport, settings=client_settings)

    user = request.user
    user.profile.token = token
    user.profile.save()

    # Accommodation Types
    AccommodationType.objects.all().delete()
    
    accommodation_service = acc_client.create_service(
        '{http://www.ftn.uns.ac.rs/tim1/accommodationsoap}AccommodationSoapPortSoap11',
        'http://40.87.122.201:8762/api/accommodation/ws/'
    )
    soap_response = accommodation_service.getAccommodationTypes()
    for atdict in soap_response:
        tmp = helpers.serialize_object(atdict['AccommodationType'])
        new_atype = AccommodationType(**tmp)
        new_atype.save()

    # Accommodation Categories
    AccommodationCategory.objects.all().delete()

    soap_response = accommodation_service.getAccommodationCategories()
    for acdict in soap_response:
        tmp = helpers.serialize_object(acdict['AccommodationCategory'])
        new_acat = AccommodationCategory(**tmp)
        new_acat.save()

    # Additional Services
    AdditionalService.objects.all().delete()

    soap_response = accommodation_service.getAdditionalServices()
    for addser in soap_response:
        tmp = helpers.serialize_object(addser['AdditionalService'])
        new_addser = AdditionalService(**tmp)
        new_addser.save()

    # Accommodations
    Accommodation.objects.all().delete()
    Location.objects.all().delete()
    try:
        soap_response = accommodation_service.getAccommodations()
    except:
        for hist in [history.last_sent, history.last_received]:
            print(etree.tostring(hist["envelope"],
                                 encoding="unicode", pretty_print=True))
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
        new_acom.accommodation_type = AccommodationType.objects.get(
            pk=atype['id'])
        new_acom.category = AccommodationCategory.objects.get(pk=acat['id'])

        new_acom.location = new_location
        new_acom.name = name
        new_acom.description = desc
        new_acom.save()

        # services
        for service in services:
            new_acom.services.add(
                AdditionalService.objects.get(pk=service['id']))

        # units
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
            soap_response = accommodation_service.getAccommodationImages(aid)
        except:
            for hist in [history.last_sent, history.last_received]:
                print(etree.tostring(hist["envelope"],
                                     encoding="unicode", pretty_print=True))
            continue
        for image_dict in soap_response:
            image_dict = image_dict['Image']

            new_image = AccommodationImage()
            new_image.id = image_dict['id']
            image_name = 'acom_#' + \
                str(new_acom.id) + '_' + str(new_image.id) + '.jpg'
            new_image.image = ContentFile(base64.b64decode(
                image_dict['value']), name=image_name)
            new_image.accommodation = new_acom
            new_image.save()

    # Reservations
    reservation_service = res_client.create_service(
        '{http://www.ftn.uns.ac.rs/tim1/reservationsoap}ReservationSoapPortSoap11',
        'http://40.87.122.201:8762/api/reservationsoap/ws/'
    )
    soap_response = reservation_service.getReservationList()
    Reservation.objects.all().delete()
    Guest.objects.all().delete()
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
