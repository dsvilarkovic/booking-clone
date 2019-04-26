import { Component, OnInit } from '@angular/core';
import { FormGroup, Validators, FormControl } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit {

  loginForm: FormGroup;

  constructor(public modal: NgbActiveModal) { }

  ngOnInit() {
    this.loginForm = new FormGroup(
      {
        password: new FormControl('', Validators.required),
        email: new FormControl('', [Validators.required, Validators.email])
      }
    );
  }

  submitForm() {
    this.modal.close(this.loginForm.value);
  }

}
