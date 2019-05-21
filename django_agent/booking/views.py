from django.shortcuts import render, get_object_or_404
from .models import AccommodationUnit, Day
from datetime import date, timedelta, datetime

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
