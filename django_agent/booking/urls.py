from django.urls import path
from . import views

app_name = 'booking'
urlpatterns = [
    path('', views.view_index, name="view_index"),
    path('planning/', views.view_prices, name="view_prices"),
    path('edit_prices/', views.edit_prices, name="edit_prices"),
]
