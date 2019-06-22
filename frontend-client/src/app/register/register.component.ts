import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { LoginService } from '../login-form/login.service';
import { User } from '../object-interfaces/user';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  registrationForm: FormGroup;
  registered = false;

  constructor(public modal: NgbActiveModal, private loginService: LoginService) { }

  ngOnInit() {
    this.registered = false;
    this.registrationForm = new FormGroup(
      {
        firstName: new FormControl('', Validators.required),
        lastName: new FormControl('', Validators.required),
        address: new FormControl('', Validators.required),
        email: new FormControl('', [Validators.required, Validators.email]),
        password: new FormControl('', Validators.required)
      }
    );
  }

  submitForm() {
    const user: User = {
      id: null,
      firstName: this.registrationForm.value.firstName,
      lastName: this.registrationForm.value.lastName,
      address: this.registrationForm.value.address,
      email: this.registrationForm.value.email,
      password: this.registrationForm.value.password,
      userType: 'registered',
      pib: null
    };
    this.loginService.register(user).subscribe(() => {
      // this.modal.close(this.registrationForm.value);
      this.registered = true;
    });
  }

}
