import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CommentsService {

  constructor(private http: HttpClient) { }

  urlBase = 'http://localhost:8080';

  // svi komentari
  getComments(): Observable<Comment> {
    const url = this.urlBase + '/comments';

    return this.http.get<Comment>(url);
  }

}

export interface Comment {
  id: number;
  value: string;
  username: string;
  administrator: string;
  date: Date;
}
