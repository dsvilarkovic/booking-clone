# Generated by Django 2.1.5 on 2019-06-20 17:30

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('booking', '0007_auto_20190614_1826'),
    ]

    operations = [
        migrations.CreateModel(
            name='AccommodationImages',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('image', models.ImageField(upload_to='accommodation_images')),
                ('accommodation', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='booking.Accommodation')),
            ],
        ),
    ]
