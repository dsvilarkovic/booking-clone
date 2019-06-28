import { Reservation } from './reservation';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from './user';
import { Review } from './review';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  urlBase = 'http://localhost:8762/api';

  // get a user
  getUser(): Observable<User> {
    const url = this.urlBase + '/users/whoami';

    // console.log(url);

    return this.http.get<User>(url);
  }

  getReservations(): Observable<Reservation[]> {
    const url = this.urlBase + '/reservations/user';

    // console.log(url);

    return this.http.get<Reservation[]>(url);
  }

  cancelReservation(id: number) {
    const url = this.urlBase + '/reservations/cancel/' + id;

    // console.log(url);

    return this.http.delete(url);
  }

  postReview(review: Review): Observable<object> {
    const url = this.urlBase + '/rating';

    console.log(url);

    return this.http.post(url, review);
  }
}
