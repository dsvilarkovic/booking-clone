# Generated by Django 2.1.5 on 2019-06-23 17:33

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('booking', '0009_auto_20190620_1951'),
    ]

    operations = [
        migrations.AddField(
            model_name='profile',
            name='token',
            field=models.CharField(blank=True, max_length=10000, null=True),
        ),
    ]
