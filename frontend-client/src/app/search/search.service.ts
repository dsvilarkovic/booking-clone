import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AccommodationUnit } from '../accommodation-profile/accommodationunit';
import { Observable } from 'rxjs';
import { AccommodationType } from './model/accommodationtype';
import { AccommodationCategory } from './model/accommodationcategory';
import { AdditionalService } from './model/additionalservice';
import { headersToString } from 'selenium-webdriver/http';

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  constructor(private http: HttpClient) { }

  // urlBase = 'http://localhost:8095'; // microservice
  urlBase = 'http://localhost:8762/api'; // zuul


  normalSearch(location: string, checkIn: Date, checkOut: Date, numberOfPersons: number, page: number): Observable<AccommodationUnit[]> {
    const httpOptions = {
      headers: new HttpHeaders({ 'Access-Control-Allow-Origin': '*', 'Content-Type': 'application/json' })
    };

    const url = this.urlBase + '/search?location=' + location + '&beginningDate=' + checkIn + '&endDate='
              + checkOut + '&numberOfPersons=' + numberOfPersons + '&page=' + page;

    console.log(url);

    return this.http.get<AccommodationUnit[]>(url, httpOptions);
  }

  advancedSearch(locationForm: string, checkInForm: Date, checkOutForm: Date, numberOfPersonsForm: number,
                 typeForm: number, categoryForm: number, servicesForm: number[], distanceForm: number,
                 page: number): Observable<AccommodationUnit[]> {
    const httpOptions = {
      headers: new HttpHeaders({ 'Access-Control-Allow-Origin': '*', 'Content-Type': 'application/json' })
    };

    const url = this.urlBase + '/search?page=' + page;

    const searchDTO = {
      location: locationForm,
      beginningDate: checkInForm,
      endDate: checkOutForm,
      numberOfPersons: numberOfPersonsForm,
      accommodationType: typeForm,
      accommodationCategory: categoryForm,
      additionalServices: servicesForm,
      distance: distanceForm
    };

    console.log(url);

    return this.http.post<AccommodationUnit[]>(url, searchDTO, httpOptions);
  }

  getAccommodationTypes(): Observable<AccommodationType[]> {
    const httpOptions = {
      headers: new HttpHeaders({ 'Access-Control-Allow-Origin': '*', 'Content-Type': 'application/json' })
    };

    const url = this.urlBase + '/maintenanceOfCodeBook/accommodationType/all';

    return this.http.get<AccommodationType[]>(url, httpOptions);
  }

  getAccommodationCategories(): Observable<AccommodationCategory[]> {
    const httpOptions = {
      headers: new HttpHeaders({ 'Access-Control-Allow-Origin': '*', 'Content-Type': 'application/json' })
    };

    const url = this.urlBase + '/maintenanceOfCodeBook/accommodationCategory/all';

    return this.http.get<AccommodationCategory[]>(url, httpOptions);
  }

  getAdditionalServices(): Observable<AdditionalService[]> {

    const httpOptions = {
      headers: new HttpHeaders({ 'Access-Control-Allow-Origin': '*', 'Content-Type': 'application/json' })
    };

    const url = this.urlBase + '/maintenanceOfCodeBook/additionalService/all';

    return this.http.get<AdditionalService[]>(url, httpOptions);
  }

}