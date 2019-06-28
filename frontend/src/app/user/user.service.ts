import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from './user';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private http: HttpClient) { }
  private httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };
  baseUrl = 'http://40.87.122.201:8762/api/user';

  /**
   * @param userType type of user (agent, admin, registered)
   */
  getUsersByType(userType: string, page: number): Observable<User> {
    const url = this.baseUrl + '/' + userType + '?page=' + page;

    return this.http.get<User>(url);
  }

  createUser(user: User, type: string) {

    const url = this.baseUrl + '/' + type;

    console.log(url);

    return this.http.post(url, user, this.httpOptions);
  }

  removeUser(id: number) {
    const url = this.baseUrl + '/'  + id;

    console.log(url);

    return this.http.delete(url);
  }

  changeUserActivation(user: User) {


    const url = this.baseUrl + '/' + user.id;
    console.log(url);

    return this.http.put(url, user, this.httpOptions);
  }

}
