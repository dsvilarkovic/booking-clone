import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { LoginFormComponent } from '../login-form/login-form.component';
import { User } from '../user/user';
import { TokenStorageService } from '../token-storage.service';
import { LoginService } from '../login-form/login.service';

@Component({
  selector: 'app-navigation-bar',
  templateUrl: './navigation-bar.component.html',
  styleUrls: ['./navigation-bar.component.css']
})
export class NavigationBarComponent implements OnInit {

  loggedUser: User = null;

  constructor(private modalService: NgbModal, private tokenStorageService: TokenStorageService, private loginService: LoginService) { }

  ngOnInit() {
    this.getUser();
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

        }});
  }
  logoutUser() {
      console.log('Logging out user');
      this.loggedUser = null;
      this.tokenStorageService.signOut();
      window.location.reload();
  }
}
