import { Component, OnInit } from '@angular/core';
import { RegisterComponent } from '../register/register.component';
import { NgbModal, NgbModalOptions } from '@ng-bootstrap/ng-bootstrap';
import { LoginFormComponent } from '../login-form/login-form.component';
import { TokenStorageService } from '../token-storage.service';
import { User } from '../user-profile/user';
import { LoginService } from '../login-form/login.service';


@Component({
  selector: 'app-navigation-bar',
  templateUrl: './navigation-bar.component.html',
  styleUrls: ['./navigation-bar.component.css']
})
export class NavigationBarComponent implements OnInit {

  loggedUser: User = null;
  modalOption: NgbModalOptions = {};

  constructor(private modalService: NgbModal, private loginService: LoginService,  private tokenStorageService: TokenStorageService) { }


  ngOnInit() {
    this.getUser();
  }

  openSignUpModal() {
    const modalRef = this.modalService.open(RegisterComponent);

    modalRef.result.then((result) => {
      console.log(result);
    }).catch((error) => {
      console.log(error);
    });
  }

  openSignInModal() {
    const modalRef = this.modalService.open(LoginFormComponent);

    modalRef.result.then((result) => {
      console.log(result);
    }).catch((error) => {
      console.log(error);
    });
  }


  getUser() {
    this.loginService.whoami().subscribe(
      data => {
        if (data != null) {
        this.loggedUser = data as User;
        console.log('najava');
        console.log(this.loggedUser);
        console.log('kraj');

        }},
        error => this.loggedUser = null);
  }
  logoutUser() {
      console.log('Logging out user');
      this.loggedUser = null;
      this.tokenStorageService.signOut();
      window.location.reload();
  }
}
