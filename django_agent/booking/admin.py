from django.contrib import admin
from django.contrib.auth.admin import UserAdmin as BaseUserAdmin
from django.contrib.auth.models import User
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


class ProfileInline(admin.StackedInline):
    model = Profile
    can_delete = False


class UserAdmin(BaseUserAdmin):
    inlines = (ProfileInline, )


class AccommodationAdmin(admin.ModelAdmin):
    list_display = ('name', 'accommodation_type', 'category')


class AccommodationUnitAdmin(admin.ModelAdmin):
    list_display = ('name', 'accommodation', 'capacity', 'default_price')


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
