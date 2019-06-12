import { Injectable, SystemJsNgModuleLoader } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Codebook } from './codebook';

@Injectable({
  providedIn: 'root'
})
export class CodebookService {
  baseUrl = 'http://localhost:8762/api/maintenanceOfCodeBook/';

  constructor(private http: HttpClient) { }

  getAllCodeBooks(page: number, codeBookType: string): Observable<object> {
    switch (codeBookType) {
      case 'ACCOMMODATION_TYPE': {
        return this.http.get(this.baseUrl + 'accommodationType' + '?page=' + page);
      }
      case 'ACCOMMODATION_CATEGORY': {
        return this.http.get(this.baseUrl + 'accommodationCategory' + '?page=' + page);
      }
      case 'ADDITIONAL_SERVICE': {
        return this.http.get(this.baseUrl + 'additionalService' + '?page=' + page);
      }
    }
  }

  getCodeBook(id: number, codeBookType: string): Observable<Codebook> {
    switch (codeBookType) {
      case 'ACCOMMODATION_TYPE': {
        return this.http.get<Codebook>(this.baseUrl + 'accommodationType/' + id);
      }
      case 'ACCOMMODATION_CATEGORY': {
        return this.http.get<Codebook>(this.baseUrl + 'accommodationCategory/' + id);
      }
      case 'ADDITIONAL_SERVICE': {
        return this.http.get<Codebook>(this.baseUrl + 'additionalService/' + id);
      }
    }
  }

  createCodeBook(codebook: Codebook) {
    const httpOptions = {
      headers: new HttpHeaders({ 'Access-Control-Allow-Origin': '*', 'Content-Type': 'application/json' })
    };
    switch (codebook.codebookType) {
      case 'ACCOMMODATION_TYPE': {
        return this.http.post(this.baseUrl + 'accommodationType', codebook, httpOptions);
      }
      case 'ACCOMMODATION_CATEGORY': {
        return this.http.post(this.baseUrl + 'accommodationCategory', codebook, httpOptions);
      }
      case 'ADDITIONAL_SERVICE': {
        return this.http.post(this.baseUrl + 'additionalService', codebook, httpOptions);
      }
    }
  }

  updateCodeBook(codebook: Codebook, id: number) {
    const httpOptions = {
      headers: new HttpHeaders({ 'Access-Control-Allow-Origin': '*', 'Content-Type': 'application/json' })
    };
    switch (codebook.codebookType) {
      case 'ACCOMMODATION_TYPE': {
        return this.http.put(this.baseUrl + 'accommodationType/' + id , codebook, httpOptions);
      }
      case 'ACCOMMODATION_CATEGORY': {
        return this.http.put(this.baseUrl + 'accommodationCategory/' + id, codebook, httpOptions);
      }
      case 'ADDITIONAL_SERVICE': {
        return this.http.put(this.baseUrl + 'additionalService/' + id, codebook, httpOptions);
      }
    }
  }

  removeCodeBook(id: number, codeBookType: string) {
    const httpOptions = {
      headers: new HttpHeaders({ 'Access-Control-Allow-Origin': '*', 'Content-Type': 'application/json' })
    };
    switch (codeBookType) {
      case 'ACCOMMODATION_TYPE': {
        return this.http.delete(this.baseUrl + 'accommodationType/' + id , httpOptions);
      }
      case 'ACCOMMODATION_CATEGORY': {
        return this.http.delete(this.baseUrl + 'accommodationCategory/' + id, httpOptions);
      }
      case 'ADDITIONAL_SERVICE': {
        return this.http.delete(this.baseUrl + 'additionalService/' + id, httpOptions);
      }
    }
  }
}
