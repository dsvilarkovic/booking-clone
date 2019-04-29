import { Injectable } from '@angular/core';
import { Observable, of} from 'rxjs';
import { Reservation, Message} from '../object-interfaces/reservation';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ChatboxService {

  private BASE_URL = 'http://localhost:3000/';

  private httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };
  constructor(private http: HttpClient) { }


  getReservations(): Observable<Reservation[]>  {
    return this.http.get<Reservation[]>(this.BASE_URL + 'reservations/');
  }

  // Citanje poruka bi trebalo vezati za rezervaciju (zbog modela), a ne za messages, ispraviti posle ko bude radio u backendu
  getMessages(): Observable<Message[]> {
    return this.http.get<Message[]>(this.BASE_URL + 'messages/');
  }

  // Slanje poruka bi trebalo vezati za rezervaciju (zbog modela), a ne za messages, ispraviti posle ko bude radio u backendu
  sendMessage(message: Message): Observable<Message[]> {
    return this.http.post<Message[]>(this.BASE_URL + 'messages/', JSON.stringify(message), this.httpOptions);
  }
}
