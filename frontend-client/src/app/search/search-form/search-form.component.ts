import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { SearchService } from '../search.service';

@Component({
  selector: 'app-search-form',
  templateUrl: './search-form.component.html',
  styleUrls: ['./search-form.component.css']
})
export class SearchFormComponent implements OnInit {

  searchForm: FormGroup;

  constructor(private router: Router,
              private searchService: SearchService) { }

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
    // redirect to the results page
    this.router.navigate(['/searchResults'], { queryParams:
               { location: this.searchForm.value.location, beginningDate: this.searchForm.value.checkInDate,
                 endDate: this.searchForm.value.checkOutDate, numberOfPersons: this.searchForm.value.numberOfPersons} });

  }

}
