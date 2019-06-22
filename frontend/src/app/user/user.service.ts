import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from './user';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private http: HttpClient) { }

  baseUrl = 'http://localhost:8762/api/user';

  /**
   * @param userType type of user (agent, admin, registered)
   */
  getUsersByType(userType: string, page: number): Observable<User> {
    const url = this.baseUrl + '/' + userType + '?page=' + page;

    return this.http.get<User>(url);
  }

  createUser(user: User, type: string) {
    const httpOptions = {
      headers: new HttpHeaders({ 'Access-Control-Allow-Origin': '*', 'Content-Type': 'application/json' })
    };
    const url = this.baseUrl + '/' + type;

    console.log(url);

    return this.http.post(url, user, httpOptions);
  }

  removeUser(id: number) {
    const httpOptions = {
      headers: new HttpHeaders({ 'Access-Control-Allow-Origin': '*' })
    };

    const url = this.baseUrl + '/'  + id;

    console.log(url);

    return this.http.delete(url, httpOptions);
  }

  changeUserActivation(user: User) {
    const httpOptions = {
      headers: new HttpHeaders({ 'Access-Control-Allow-Origin': '*', 'Content-Type': 'application/json' })
    };

    const url = this.baseUrl + '/' + user.id;
    console.log(url);

    return this.http.put(url, user, httpOptions);
  }

}
