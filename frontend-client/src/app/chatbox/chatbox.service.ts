import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Reservation, Message, ReservationAccommodationInfo} from '../object-interfaces/reservation';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ChatboxService {

  private BASE_URL = 'http://localhost:8762/api/';

  private httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }


  getReservations(): Observable<ReservationAccommodationInfo[]> {
    return this.http.get<ReservationAccommodationInfo[]>(this.BASE_URL + 'reservations/user');
  }

  // Citanje poruka bi trebalo vezati za rezervaciju (zbog modela), a ne za messages, ispraviti posle ko bude radio u backendu
  getMessages(reservationId: number): Observable<Message[]> {
    return this.http.get<Message[]>(this.BASE_URL + 'clientMessage/userReservation/' + reservationId);
  }

  // Slanje poruka bi trebalo vezati za rezervaciju (zbog modela), a ne za messages, ispraviti posle ko bude radio u backendu
  sendMessage(message: Message): Observable<Message> {
    return this.http.post<Message>(this.BASE_URL + 'clientMessage/', JSON.stringify(message), this.httpOptions);
  }


}
