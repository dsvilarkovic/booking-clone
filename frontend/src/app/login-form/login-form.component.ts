import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
// import { userInfo } from 'os';
import { LoginUser} from './login-user';
import { LoginService } from './login.service';
import { User } from '../user/user';
import { TokenStorageService } from '../token-storage.service';
import { Token } from '../token';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit {
  token: Token = new Token();
  loginForm: FormGroup;

  constructor(public modal: NgbActiveModal, private loginService: LoginService, private tokenStorageService: TokenStorageService) { }

  ngOnInit() {
    this.loginForm = new FormGroup(
      {
        password: new FormControl('', Validators.required),
        email: new FormControl('', [Validators.required, Validators.email])
      }
    );
  }

  submitForm() {
    const loginUser: LoginUser = {username : this.loginForm.value.email, password : this.loginForm.value.password};
    this.loginService.login(loginUser).subscribe(res => {
        console.log(res);
        let jwtToken = res.headers.get('Authorization');
        jwtToken = jwtToken.replace('Bearer ', '');
        console.log(jwtToken);
        this.tokenStorageService.saveToken(jwtToken);
        this.modal.close(this.loginForm.value);

        this.loginService.whoami().subscribe(data => {
            this.tokenStorageService.saveUser(data);
            window.location.reload();
        });
    });
  }

  whoami(): User {
    this.loginService.whoami().subscribe(data => {
       this.tokenStorageService.saveUser(data);
       return data as User;
    });
    return null;
  }

  logout() {
    this.tokenStorageService.signOut();
  }


}
