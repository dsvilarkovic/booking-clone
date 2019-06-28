import { Accommodation } from './accommodation';
import { AccommodationUnit } from './accommodationunit';
import { Image } from './image';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class AccommodationService {

  constructor(private http: HttpClient) { }

  urlBase = 'http://localhost:8762/api';

  // get an accommodation
  getAccommodation(id: number): Observable<Accommodation> {
    const url = this.urlBase + '/accommodationService/accommodation/' + id;

    // console.log(url);

    return this.http.get<Accommodation>(url);
  }

  // get accommodation units of an accommodation
  getAccommodationUnits(id: number, page: number): Observable<AccommodationUnit[]> {
    const url = this.urlBase + '/accommodationService/accommodation/accommodationUnits/' + id + '?page=' + page;

    // console.log(url);

    return this.http.get<AccommodationUnit[]>(url);
  }

  // get accommodation images
  getAccommodationImages(id: number): Observable<Image[]>{
    const url = this.urlBase + '/accommodationService/accommodation/images/' + id;

    // console.log(url);

    return this.http.get<Image[]>(url);
  }

  // get accommodation comments
  getComments(id: number): Observable<Comment[]> {
    const url = this.urlBase + '/comments/comments/accommodation/' + id;
    return this.http.get<Comment[]>(url);
  }
}
