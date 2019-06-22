import { Comment } from './comment';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { strictEqual } from 'assert';


@Injectable({
  providedIn: 'root'
})


export class CommentsService {
  urlBase = 'http://localhost:8762/api/comments';

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':  'application/json',
      Accept : 'text/plain, application/json',
      Authorization: ''
    }),
  };
  plainTexthttpOptions = {
    headers: new HttpHeaders({
      'Content-Type':  'application/json',
      Accept : 'text/plain, application/json',
      Authorization: ''
    }),
    responseType: 'text' as 'json'
  };
  constructor(private http: HttpClient) { }


  // get all comments
  getComments(pageNo: number, pageSize: number): Observable<any[]> {
    const url = this.urlBase + '/comments?' + 'page=' + (pageNo - 1) + '&size=' + (pageSize);

    return this.http.get<any[]>(url, this.httpOptions);
  }

  // approve/reject a comment
  approveComment(comment: Comment, approve: boolean) {
    const url = this.urlBase + '/comments/approve/' + comment.id + '/' + (approve ? 'true' : 'false');
    console.log(url);

    return this.http.put(url, comment, this.plainTexthttpOptions);
  }

}

