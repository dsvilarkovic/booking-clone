import { Component, OnInit } from '@angular/core';
import { AccommodationUnit } from '../accommodation-profile/accommodationunit';
import { Reservation } from './reservation';
import { User } from '../object-interfaces/user';
import { ReservationCheckoutService } from './reservation-checkout.service';
import { Router } from '@angular/router';
import { Location } from '@angular/common';


@Component({
  selector: 'app-reservation-checkout',
  templateUrl: './reservation-checkout.component.html',
  styleUrls: ['./reservation-checkout.component.css']
})
export class ReservationCheckoutComponent implements OnInit {

  constructor(private reservationCheckoutService: ReservationCheckoutService, private location: Location) { 
  }

  beginningDate: any = null;
  endDate: any = null;
  difInDays = 0;

  accommodationUnit: AccommodationUnit = null;
  reservationDone = false;

  user: User = {
    id: 1,
    firstName: 'Dusan',
    lastName: 'Dusanovic',
    address: 'Vuka Mandusica 17',
    email: 'dusan@gmail.com',
    password: null,
    pib: null,
    userType: 'registered'
  };

  reservation: Reservation = {
    id: null,
    accommodationUnit : this.accommodationUnit,
    beginningDate : null,
    endDate : null,
    checkedIn : false,
    finalPrice : 0,
    user : this.user
  };

  ngOnInit() {
    this.accommodationUnit = JSON.parse(sessionStorage.getItem('accommodationUnit'));
    this.reservation.accommodationUnit = this.accommodationUnit;
  }

  calculateReservation(value) {
    console.log(this.beginningDate);
    console.log(this.endDate);

    const date1 = (new Date(this.beginningDate)).getTime();
    const date2 = (new Date(this.endDate)).getTime();

    this.difInDays = (date2 - date1) / 8.64e7;

    console.log(date1 + ' ' + date2);
    console.log(this.difInDays);


    this.reservation.beginningDate = date1;
    this.reservation.endDate = date2;

    if (this.difInDays > 0) {
      this.reservation.finalPrice = this.difInDays * this.accommodationUnit.defaultPrice;
    } else {
      this.reservation.finalPrice = 0;
    }
  }

  confirmReservation() {
    this.reservationCheckoutService.reserve(this.reservation).subscribe(() => {
      sessionStorage.setItem('accommodationUnit', null);
      this.reservationDone = true;
    });
  }


}
