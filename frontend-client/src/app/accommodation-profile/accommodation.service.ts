import { Accommodation } from './accommodation';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class AccommodationService {

  constructor(private http: HttpClient) { }

  urlBase = 'http://localhost:8080';

  // get an accommodation
  getAccommodation(id: number): Observable<Accommodation> {
    const url = this.urlBase + '/accommodation/' + id;

    return this.http.get<Accommodation>(url);
  }
}
