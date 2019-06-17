import { Injectable } from '@angular/core';
import { Reservation } from './reservation';
import { HttpClientModule, HttpHeaders, HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ReservationCheckoutService {

  constructor(private http: HttpClient) { }

  // TODO: Ovo izmeniti za link url
  reservationUrl = 'localhost:8762/api/reservation/';
  private reservation: Reservation =  null;
  reserve(reservation: Reservation) {
    // TODO: ovde rezervisati
    return this.http.post(this.reservationUrl, this.reservation);
  }
}
