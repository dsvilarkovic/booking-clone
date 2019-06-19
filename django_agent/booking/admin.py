from django.contrib import admin
from django.contrib.auth.admin import UserAdmin as BaseUserAdmin
from django.contrib.auth.models import User
from django.conf import settings
from django.forms.models import model_to_dict
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
#import pdb; pdb.set_trace()

class ProfileInline(admin.StackedInline):
    model = Profile
    can_delete = False


class UserAdmin(BaseUserAdmin):
    inlines = (ProfileInline, )


class AccommodationAdmin(admin.ModelAdmin):
    list_display = ('name', 'accommodation_type', 'category')

    def save_model(self, request, obj, form, change):
        history = HistoryPlugin()
        client_settings = Settings(strict=False, xml_huge_tree=True, xsd_ignore_sequence_order=True)
        client = Client(settings.WSDL_ADDRESS_ACCOMMODATION, settings=client_settings, plugins=[history])
        
        obj.user = request.user
        acom = obj
        if acom.pk is None:
            #create
            acom.save()
            transfer = acom.to_dict()
            transfer['id'] = ''
            transfer['Location']['id'] = ''
            loc = acom.location
            Location.objects.filter(id=loc.id).delete()

            #AdditionalServices
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
            import pdb; pdb.set_trace()
            #update
            transfer = acom.to_dict()
            #AdditionalServices
            servs = []
            for service in form.cleaned_data['services'].all():
                servs.append(service.to_dict())
            transfer['AdditionalService'] = servs
            transfer = {'Accommodation': transfer}
            try:
                response = client.service.updateAccommodation(**transfer)
            except:
                for hist in [history.last_sent, history.last_received]:
                    print(etree.tostring(hist["envelope"], encoding="unicode", pretty_print=True))

        super().save_model(request, obj, form, change)
    
    def delete_model(self, request, obj):
        history = HistoryPlugin()
        client_settings = Settings(strict=False, xml_huge_tree=True, xsd_ignore_sequence_order=True)
        client = Client(settings.WSDL_ADDRESS_ACCOMMODATION, settings=client_settings, plugins=[history])
        transfer = dict()
        transfer['accommodation_id'] = obj.id
        try:
            response = client.service.deleteAccommodation(**transfer)
        except:
            for hist in [history.last_sent, history.last_received]:
                print(etree.tostring(hist["envelope"], encoding="unicode", pretty_print=True))
        super().delete_model(request, obj) 


class AccommodationUnitAdmin(admin.ModelAdmin):
    list_display = ('name', 'accommodation', 'capacity', 'default_price')

    def save_model(self, request, obj, form, change):
        history = HistoryPlugin()
        client_settings = Settings(strict=False, xml_huge_tree=True, xsd_ignore_sequence_order=True)
        client = Client(settings.WSDL_ADDRESS_ACCOMMODATION, settings=client_settings, plugins=[history])

        unit = obj

        if unit.pk is None:
            #create
            unit.save()
            accommodation_id = unit.accommodation.id
            transfer = unit.to_dict()
            transfer = {'AccommdationUnit': transfer, 'accommodation_id': accommodation_id}
            response = client.service.createAccommodationUnit(**transfer)
        else:
            #update
            transfer = unit.to_dict()
            transfer = {'AccommdationUnit': transfer}
            response = client.service.updateAccommodationUnit(**transfer)
            import pdb; pdb.set_trace()




        super().save_model(request, obj, form, change)


class GuestAdmin(admin.ModelAdmin):
    list_display = ('first_name', 'last_name', 'email')

class DayAdmin(admin.ModelAdmin):
    list_display = ('unit', 'date', 'price', 'available')
    search_fields = ('unit__name', )


admin.site.unregister(User)
admin.site.register(User, UserAdmin)
admin.site.register(Accommodation, AccommodationAdmin)
admin.site.register(AccommodationUnit, AccommodationUnitAdmin)
admin.site.register(Guest, GuestAdmin)
admin.site.register(Profile)
admin.site.register(AdditionalService)
admin.site.register(Location)
admin.site.register(Day, DayAdmin)

admin.site.register(AccommodationType)
admin.site.register(AccommodationCategory)


admin.site.register(Reservation)
admin.site.register(Message)
