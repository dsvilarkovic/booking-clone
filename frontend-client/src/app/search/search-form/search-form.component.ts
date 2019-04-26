import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-search-form',
  templateUrl: './search-form.component.html',
  styleUrls: ['./search-form.component.css']
})
export class SearchFormComponent implements OnInit {

  searchForm: FormGroup;

  constructor(private router: Router) { }

  ngOnInit() {
    this.searchForm = new FormGroup(
      {
        location: new FormControl('', Validators.required),
        checkInDate: new FormControl('', Validators.required),
        checkOutDate: new FormControl('', Validators.required),
        numberOfPersons: new FormControl('', Validators.required )
      }
    );
  }

  searchFormSubmit() {
    this.router.navigateByUrl('/searchResults');
  }

}
