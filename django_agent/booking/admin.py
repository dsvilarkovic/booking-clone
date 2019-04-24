from django.contrib import admin
from .models import Accommodation
from .models import AccommodationCategory
from .models import AccommodationType
from .models import AccommodationUnit
from .models import Location
from .models import AdditionalService
from .models import Day


admin.site.register(AccommodationType)
admin.site.register(AccommodationCategory)
admin.site.register(Location)
admin.site.register(Accommodation)
admin.site.register(AccommodationUnit)
admin.site.register(AdditionalService)
admin.site.register(Day)
