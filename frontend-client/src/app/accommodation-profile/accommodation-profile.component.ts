import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AccommodationService } from './accommodation.service';
import { Accommodation } from './accommodation';
import { Location } from './location';

@Component({
  selector: 'app-accommodation-profile',
  templateUrl: './accommodation-profile.component.html',
  styleUrls: ['./accommodation-profile.component.css']
})
export class AccommodationProfileComponent implements OnInit {

  id: number;
  // accommodation: Accommodation;

  constructor(private router: ActivatedRoute,
              private accommodationService: AccommodationService) { }

  loc: Location = {
    id: 0,
    address: 'Polgar Andraša 1',
    city: 'Novi Sad',
    country: 'Serbia',
    longitude: 19.8140651,
    latitude: 45.2482027
  };

  accommodation: Accommodation = {
    id: 0,
    name: 'Sheraton',
    description: 'Ovo je opis hotela Sheraton.',
    rating: 4.5,
    location: this.loc
  };

  ngOnInit() {
    // get accommodation id from the path
    this.router.paramMap.subscribe(
      params => {
        this.id = +params.get('id');
      });

    // getAccommodation(id: number)
  }

  getAccommodation(id: number) {
    /* this.accommodationService.getAccommodation(this.id).subscribe(
      data => {
        this.accommodation = data;
      },
      error => {
        console.log('There was an error.');
      }
    ); */
  }

}
