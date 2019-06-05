from django.shortcuts import render, get_object_or_404, redirect
from .models import AccommodationUnit, Day, Guest, Message, Reservation, AccommodationType, AccommodationCategory, Accommodation
from datetime import date, timedelta, datetime
from zeep import Client, Settings, helpers
#import pdb; pdb.set_trace()


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
            # TODO: Add backend logic here
        return render(request, 'booking/view_prices.html')


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

WSDL_ADDRESS = 'http://localhost:9998/ws/accommodationsoap.wsdl'
def sync_all_data(request):
    settings = Settings(strict=False, xml_huge_tree=True)
    client = Client(WSDL_ADDRESS, settings=settings)

    # Accommodation Types
    AccommodationType.objects.all().delete()

    soap_response = client.service.getAccommodationTypes()
    for atdict in soap_response:
        tmp = helpers.serialize_object(atdict['AccommodationType'])
        new_atype = AccommodationType(**tmp)
        new_atype.save()


    # Accommodation Categories
    AccommodationCategory.objects.all().delete()
    
    soap_response = client.service.getAccommodationCategories()
    for acdict in soap_response:
        tmp = helpers.serialize_object(acdict['AccommodationCategory'])
        new_acat = AccommodationCategory(**tmp)
        new_acat.save()


    return render(request, 'booking/view_index.html')
