import { Injectable } from '@angular/core';
import { ReservationDTO } from './reservation';
import { HttpClientModule, HttpHeaders, HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ReservationCheckoutService {
  private httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };
  constructor(private http: HttpClient) { }

  // TODO: Ovo izmeniti za link url
  reservationUrl = 'http://40.87.122.201:8762/api/reservations';
  reserve(reservation: ReservationDTO) {
    // TODO: ovde rezervisati
    console.log(reservation);
    return this.http.post(this.reservationUrl, reservation, this.httpOptions);
  }
}
