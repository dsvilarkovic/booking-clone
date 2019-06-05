from django.db import models
from django.core.exceptions import ValidationError
from django.contrib.auth.models import User


class AccommodationType(models.Model):
    name = models.CharField(max_length=60, unique=True)

    def __str__(self):
        return self.name


class AccommodationCategory(models.Model):
    name = models.CharField(max_length=60, unique=True)

    def __str__(self):
        return str(self.name) + ' star'


class Location(models.Model):
    address = models.CharField(max_length=100)
    city = models.CharField(max_length=50)
    country = models.CharField(max_length=50)
    longitude = models.DecimalField(
        decimal_places=8,
        max_digits=11
    )
    latitude = models.DecimalField(
        decimal_places=8,
        max_digits=11
    )

    def __str__(self):
        return self.address + ', ' + self.city


class AdditionalService(models.Model):
    name = models.CharField(max_length=60, unique=True)

    def __str__(self):
        return self.name


class Accommodation(models.Model):
    accommodation_type = models.ForeignKey(
        AccommodationType, on_delete=models.CASCADE)
    category = models.ForeignKey(
        AccommodationCategory, on_delete=models.CASCADE)
    location = models.OneToOneField(Location, on_delete=models.CASCADE)
    name = models.CharField(max_length=60)
    description = models.TextField(max_length=200, blank=True)
    services = models.ManyToManyField(AdditionalService, blank=True)
    # image?

    def __str__(self):
        return self.name

    def save(self, *args, **kwargs):
        super().save(*args, **kwargs)
        # TODO: Add backend logic here


class AccommodationUnit(models.Model):
    accommodation = models.ForeignKey(Accommodation, on_delete=models.CASCADE)
    name = models.CharField(max_length=60)
    capacity = models.PositiveIntegerField()
    default_price = models.DecimalField(
        decimal_places=2,
        max_digits=10
    )
    cancelation_period = models.PositiveIntegerField()

    def __str__(self):
        return self.name + ' | ' + str(self.accommodation)


class Day(models.Model):
    unit = models.ForeignKey(AccommodationUnit, on_delete=models.CASCADE)
    date = models.DateField()
    price = models.DecimalField(
        decimal_places=2,
        max_digits=10
    )
    available = models.BooleanField(default=True)

    class Meta:
        unique_together = ('unit', 'date')

    def clean(self):
        if self.price < 0:
            raise ValidationError("Price cannot be negative")

    def __str__(self):
        return str(self.date)


class Guest(models.Model):
    first_name = models.CharField(max_length=50)
    last_name = models.CharField(max_length=50)
    email = models.EmailField()

    def __str__(self):
        return self.first_name + ' ' + self.last_name


class Reservation(models.Model):
    unit = models.ForeignKey(AccommodationUnit, on_delete=models.CASCADE)
    guest = models.ForeignKey(Guest, on_delete=models.CASCADE)
    beginning = models.DateField()
    end = models.DateField()
    final_price = models.DecimalField(
        decimal_places=2,
        max_digits=10
    )
    checked_in = models.BooleanField()

    def clean(self):
        if self.final_price < 0:
            raise ValidationError("Charge cannot be negative")
        if self.end < self.beginning:
            raise ValidationError("Interval incorrect")


class Message(models.Model):
    reservation = models.ForeignKey(Reservation, on_delete=models.CASCADE)
    text = models.TextField(280)
    timestamp = models.DateTimeField()
    mine = models.BooleanField()


class Profile(models.Model):
    user = models.OneToOneField(User, on_delete=models.CASCADE)
    address = models.OneToOneField(Location, on_delete=models.CASCADE)
    pib = models.CharField(max_length=9)

    def __str__(self):
        return str(self.user)
