# Generated by Django 2.1.5 on 2019-04-21 21:41

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Accommodation',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('name', models.CharField(max_length=60)),
                ('description', models.TextField(blank=True, max_length=200)),
            ],
        ),
        migrations.CreateModel(
            name='AccommodationCategory',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('value', models.IntegerField(unique=True)),
            ],
        ),
        migrations.CreateModel(
            name='AccommodationType',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('name', models.CharField(max_length=60, unique=True)),
            ],
        ),
        migrations.CreateModel(
            name='AccommodationUnit',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('name', models.CharField(max_length=60)),
                ('capacity', models.PositiveIntegerField()),
                ('default_price', models.DecimalField(decimal_places=2, max_digits=10)),
                ('cancelation_period', models.PositiveIntegerField()),
                ('accommodation', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='booking.Accommodation')),
            ],
        ),
        migrations.CreateModel(
            name='AdditionalService',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('name', models.CharField(max_length=60, unique=True)),
            ],
        ),
        migrations.CreateModel(
            name='Day',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('date', models.DateField()),
                ('price', models.DecimalField(decimal_places=2, max_digits=10)),
                ('available', models.BooleanField(default=True)),
                ('unit', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='booking.AccommodationUnit')),
            ],
        ),
        migrations.CreateModel(
            name='Location',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('address', models.CharField(max_length=100)),
                ('city', models.CharField(max_length=50)),
                ('country', models.CharField(max_length=50)),
                ('longitude', models.DecimalField(decimal_places=8, max_digits=11)),
                ('latitude', models.DecimalField(decimal_places=8, max_digits=11)),
            ],
        ),
        migrations.AddField(
            model_name='accommodation',
            name='accommodation_type',
            field=models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='booking.AccommodationType'),
        ),
        migrations.AddField(
            model_name='accommodation',
            name='category',
            field=models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='booking.AccommodationCategory'),
        ),
        migrations.AddField(
            model_name='accommodation',
            name='location',
            field=models.OneToOneField(on_delete=django.db.models.deletion.CASCADE, to='booking.Location'),
        ),
    ]
