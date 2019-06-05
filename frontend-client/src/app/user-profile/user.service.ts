import { Reservation } from './../object-interfaces/reservation';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from './user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  urlBase = 'http://localhost:8080';

  // get a user
  getUser(id: number): Observable<User> {
    const url = this.urlBase + '/user/' + id;

    return this.http.get<User>(url);
  }

  getReservations(id: number): Observable<Reservation[]>{
    const url = this.urlBase + 'reservation/user/' + id;

    return this.http.get<Reservation[]>(url);
  }
}
