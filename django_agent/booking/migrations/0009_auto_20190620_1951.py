# Generated by Django 2.1.5 on 2019-06-20 17:51

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('booking', '0008_accommodationimages'),
    ]

    operations = [
        migrations.RenameModel(
            old_name='AccommodationImages',
            new_name='AccommodationImage',
        ),
    ]