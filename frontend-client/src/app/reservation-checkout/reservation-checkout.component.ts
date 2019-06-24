import { Component, OnInit } from '@angular/core';
import { Reservation, ReservationDTO } from './reservation';
import { User } from '../object-interfaces/user';
import { ReservationCheckoutService } from './reservation-checkout.service';
import { DatePipe } from '@angular/common';
import { TokenStorageService } from '../token-storage.service';
import { LoginService } from '../login-form/login.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-reservation-checkout',
  templateUrl: './reservation-checkout.component.html',
  styleUrls: ['./reservation-checkout.component.css']
})
export class ReservationCheckoutComponent implements OnInit {

  constructor(private reservationCheckoutService: ReservationCheckoutService,
              private tokenStorage: TokenStorageService,
              private loginService: LoginService,
              private datePipe: DatePipe,
              private router: Router) {}


  beginningDate: any = null;
  endDate: any = null;
  difInDays = 0;
  reservationDone = false;

  user: User = {
    firstName: '',
    lastName: '',
    email: '',
    address: '',
    id: -1,
    password: '',
    pib: -1,
    userType: ''
  };
  reservation: Reservation = {
    id: null,
    accommodationUnit: null,
    beginningDate: null,
    endDate: null,
    checkedIn: false,
    finalPrice: 0,
    userId: null,
    numberOfPersons: -1
  };

  ngOnInit() {
    if (this.tokenStorage.getUser() == null) {
      this.router.navigateByUrl('/');
    }

    this.loginService.whoami().subscribe(
      data => {
        this.user = data;
      }, error => {
        // preusmeri negde
        this.router.navigateByUrl('/');
      }
    );
    const reservationString = sessionStorage.getItem('reservation');
    if (reservationString == null) {
      this.router.navigateByUrl('/');
    } else {
      this.reservation = JSON.parse(reservationString);
      this.reservation.userId = this.user.id;
      this.beginningDate = this.datePipe.transform(new Date(this.reservation.beginningDate), 'yyyy-MM-dd');
      this.endDate = this.datePipe.transform(new Date(this.reservation.endDate), 'yyyy-MM-dd');
      const date1 = (new Date(this.beginningDate)).getTime();
      const date2 = (new Date(this.endDate)).getTime();

      this.difInDays = (date2 - date1) / 8.64e7;
      if (this.difInDays > 0) {
        this.reservation.finalPrice = this.difInDays * this.reservation.accommodationUnit.defaultPrice;
      } else {
        this.reservation.finalPrice = 0;
      }
    }

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
      this.reservation.finalPrice = this.difInDays * this.reservation.accommodationUnit.defaultPrice;
    } else {
      this.reservation.finalPrice = 0;
    }
  }

  confirmReservation() {
    const reservation: ReservationDTO = new ReservationDTO();
    reservation.accommodationUnitId = this.reservation.accommodationUnit.id;
    reservation.beginningDate = (new Date(this.beginningDate)).getTime();
    reservation.endDate = (new Date(this.endDate)).getTime();
    reservation.checkedIn = false;
    reservation.commentId = -1;
    reservation.ratingId = -1;
    reservation.numberOfPersons = this.reservation.numberOfPersons;
    reservation.id = null;
    reservation.userId = this.user.id;
    this.reservationCheckoutService.reserve(reservation).subscribe( data => {
      sessionStorage.setItem('reservation', null);
      this.reservationDone = true;
    }, erorr => {
      alert(erorr.error);
    }
    );
  }


}
