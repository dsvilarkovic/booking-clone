from django.contrib import admin
from django.contrib.auth.admin import UserAdmin as BaseUserAdmin
from django.contrib.auth.models import User
from django.conf import settings
from django.forms.models import model_to_dict
from datetime import date, timedelta, datetime
from django.utils.safestring import mark_safe
from lxml import etree
from zeep import Client, Settings, helpers
from zeep.plugins import HistoryPlugin
from .models import Accommodation
from .models import AccommodationCategory
from .models import AccommodationType
from .models import AccommodationUnit
from .models import Location
from .models import AdditionalService
from .models import Day
from .models import Profile
from .models import Guest
from .models import Reservation
from .models import Message
from .models import AccommodationImage
import pdb

import base64
from django.core.files.base import ContentFile


class ProfileInline(admin.StackedInline):
    model = Profile
    can_delete = False


class UserAdmin(BaseUserAdmin):
    inlines = (ProfileInline, )


class ImageInline(admin.StackedInline):
    model = AccommodationImage
    readonly_fields = ['show_image', ]

    def show_image(self, obj):
        return mark_safe('<img src="{url}" width=200 height=150 />'.format(
            url=obj.image.url)
        )


class AccommodationAdmin(admin.ModelAdmin):
    list_display = ('name', 'accommodation_type', 'category')
    inlines = (ImageInline,)

    def save_model(self, request, obj, form, change):
        history = HistoryPlugin()
        client_settings = Settings(
            strict=False, xml_huge_tree=True, xsd_ignore_sequence_order=True)
        client = Client(settings.WSDL_ADDRESS_ACCOMMODATION,
                        settings=client_settings, plugins=[history])

        obj.user = request.user
        acom = obj
        if acom.pk is None:
            # create
            acom.save()
            transfer = acom.to_dict()
            transfer['id'] = ''
            transfer['Location']['id'] = ''
            loc = acom.location
            Location.objects.filter(id=loc.id).delete()

            # AdditionalServices
            servs = []
            for service in form.cleaned_data['services'].all():
                servs.append(service.to_dict())
            transfer['AdditionalService'] = servs

            transfer = {'Accommodation': transfer}
            response = client.service.createAccommodation(**transfer)

            loc.id = response['location_id']
            acom.id = response['accommodation_id']
            acom.location_id = response['location_id']
            loc.save()
        else:
            # update
            transfer = acom.to_dict()
            # AdditionalServices
            servs = []
            for service in form.cleaned_data['services'].all():
                servs.append(service.to_dict())
            transfer['AdditionalService'] = servs
            transfer = {'Accommodation': transfer}
            try:
                response = client.service.updateAccommodation(**transfer)
            except:
                for hist in [history.last_sent, history.last_received]:
                    print(etree.tostring(
                        hist["envelope"], encoding="unicode", pretty_print=True))

        super().save_model(request, obj, form, change)

    def delete_model(self, request, obj):
        history = HistoryPlugin()
        client_settings = Settings(
            strict=False, xml_huge_tree=True, xsd_ignore_sequence_order=True)
        client = Client(settings.WSDL_ADDRESS_ACCOMMODATION,
                        settings=client_settings, plugins=[history])
        transfer = dict()
        transfer['accommodation_id'] = obj.id
        try:
            response = client.service.deleteAccommodation(**transfer)
        except:
            for hist in [history.last_sent, history.last_received]:
                print(etree.tostring(hist["envelope"],
                                     encoding="unicode", pretty_print=True))
        super().delete_model(request, obj)

    def save_formset(self, request, form, formset, change):
        if formset.model != AccommodationImage:
            return super().save_formset(request, form, formset, change)
        else:
            instances = formset.save(commit=False)
            acc_client = Client(settings.WSDL_ADDRESS_ACCOMMODATION)
            for obj in formset.deleted_objects:
                transfer = dict()
                transfer['image_id'] = obj.id
                acc_client.service.deleteImage(**transfer)
                obj.delete()
            for instance in instances:
                transfer = dict()
                transfer['Image'] = dict()
                transfer['Image']['id'] = 1000
                transfer['Image']['value'] = base64.b64encode(
                    instance.image.read())
                transfer['accommodation_id'] = instance.accommodation.id

                response = acc_client.service.createImage(**transfer)
                instance.id = response
                instance.save()


class AccommodationUnitAdmin(admin.ModelAdmin):
    list_display = ('name', 'accommodation', 'capacity', 'default_price')

    def save_model(self, request, obj, form, change):
        history = HistoryPlugin()
        client_settings = Settings(
            strict=False, xml_huge_tree=True, xsd_ignore_sequence_order=True)
        client = Client(settings.WSDL_ADDRESS_ACCOMMODATION,
                        settings=client_settings, plugins=[history])

        unit = obj
        if unit.pk is None:
            # create
            unit.save()
            accommodation_id = unit.accommodation.id
            transfer = unit.to_dict()
            transfer = {'AccommodationUnit': transfer,
                        'accommodation_id': accommodation_id}
            response = client.service.createAccommodationUnit(**transfer)
            AccommodationUnit.objects.filter(id=unit.id).delete()
            unit.id = response
        else:
            # update
            transfer = unit.to_dict()
            transfer = {'AccommodationUnit': transfer}
            response = client.service.updateAccommodationUnit(**transfer)

            if unit.day_set.exists():
                unit.day_set.all().delete()
                for elem in response['_value_1']:
                    day_dict = helpers.serialize_object(elem['Day'])
                    day = Day()
                    day.unit = unit
                    day.date = date.fromtimestamp(day_dict['date'] / 1000)
                    day.price = day_dict['price']
                    day.available = day_dict['available']
                    day.save()

        super().save_model(request, obj, form, change)

    def delete_model(self, request, obj):
        history = HistoryPlugin()
        client_settings = Settings(
            strict=False, xml_huge_tree=True, xsd_ignore_sequence_order=True)
        client = Client(settings.WSDL_ADDRESS_ACCOMMODATION,
                        settings=client_settings, plugins=[history])
        transfer = dict()
        transfer['accommodation_unit_id'] = obj.id
        try:
            response = client.service.deleteAccommodationUnit(**transfer)
        except:
            for hist in [history.last_sent, history.last_received]:
                print(etree.tostring(hist["envelope"],
                                     encoding="unicode", pretty_print=True))
        super().delete_model(request, obj)


class GuestAdmin(admin.ModelAdmin):
    list_display = ('first_name', 'last_name', 'email')

    def has_add_permission(self, request, obj=None):
        return False

    def has_change_permission(self, request, obj=None):
        return False

    def has_delete_permission(self, request, obj=None):
        return False


class DayAdmin(admin.ModelAdmin):
    list_display = ('unit', 'date', 'price', 'available')
    search_fields = ('unit__name', )


class ReservationAdmin(admin.ModelAdmin):
    list_display = ('unit', 'guest', 'beginning',
                    'end', 'final_price', 'checked_in')
    readonly_fields = ['unit', 'guest', 'beginning', 'end', 'final_price']

    def save_model(self, request, obj, form, change):
        res = obj
        pdb.set_trace()
        if res.checked_in is True:
            client = Client(settings.WSDL_ADDRESS_RESERVATION)
            client.service.checkinReservation(reservation_id=res.id)
        super().save_model(request, obj, form, change)

    def has_add_permission(self, request, obj=None):
        return False

    def has_delete_permission(self, request, obj=None):
        return False


class AccommodationTCAdmin(admin.ModelAdmin):
    def has_add_permission(self, request, obj=None):
        return False

    def has_change_permission(self, request, obj=None):
        return False

    def has_delete_permission(self, request, obj=None):
        return False


admin.site.unregister(User)
admin.site.register(User, UserAdmin)
admin.site.register(Accommodation, AccommodationAdmin)
admin.site.register(AccommodationUnit, AccommodationUnitAdmin)
admin.site.register(Guest, GuestAdmin)
# admin.site.register(Profile)

# admin.site.register(Location)
# admin.site.register(Day, DayAdmin)

admin.site.register(AccommodationType, AccommodationTCAdmin)
admin.site.register(AccommodationCategory, AccommodationTCAdmin)
admin.site.register(AdditionalService, AccommodationTCAdmin)

admin.site.register(Reservation, ReservationAdmin)
