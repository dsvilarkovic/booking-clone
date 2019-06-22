import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LoginUser } from './login-user';
import { GATEWAY_URL } from '../global';
import { User } from '../object-interfaces/user';

// const httpOptions = {
//   headers: new HttpHeaders({'Content-Type':  'application/json',   Accept : 'text/plain, application/json' }),
//   observe : 'response' as response
// };

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    Accept : 'text/plain, application/json'
  }),
  observe: 'response' as 'body'
};

const httpOptionsTextResponse = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    Accept : 'text/plain, application/json'
  }),
  responseType: 'text' as 'json'
};

@Injectable({
  providedIn: 'root'
})

export class LoginService {

  authUrl = GATEWAY_URL + 'api/auth/';
  usersUrl = GATEWAY_URL + 'api/users/';

  constructor(private http: HttpClient) { }

  login(user: LoginUser): Observable<any> {
      // console.log(user);
      return this.http.post(this.authUrl, user, httpOptions );
  }


  // To return User data, i had to use different headers
  whoami(): Observable<any> {
      return this.http.get(this.usersUrl + 'whoami',
      {   headers: new HttpHeaders({
          'Content-Type': 'application/json',
          Accept : 'text/plain, application/json'
        })});
  }

  register(user: User): Observable<any> {
    // console.log('Pocetak');
    // console.log(user);
    // console.log('Kraj');
    return this.http.post(this.usersUrl + '',
      {
        firstName: user.firstName,
        lastName: user.lastName,
        username: user.email,
        password: user.password,
        address: user.address,
        userType: 'registered'
      }, httpOptionsTextResponse );
  }
}
