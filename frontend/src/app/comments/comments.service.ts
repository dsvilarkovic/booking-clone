import { Comment } from './comment';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class CommentsService {

  constructor(private http: HttpClient) { }

  urlBase = 'http://localhost:8080';

  // get all comments
  getComments(): Observable<Comment[]> {
    const url = this.urlBase + '/comments';

    return this.http.get<Comment[]>(url);
  }

  // approve/reject a comment
  approveComment(comment: Comment) {
    const url = this.urlBase + '/comments/' + comment.id;

    return this.http.put(url, comment);
  }

}

