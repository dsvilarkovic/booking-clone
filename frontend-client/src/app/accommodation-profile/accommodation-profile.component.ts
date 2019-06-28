
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AccommodationService } from './accommodation.service';
import { Accommodation } from './accommodation';
import { Location } from './location';
import { AccommodationUnit } from './accommodationunit';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-accommodation-profile',
  templateUrl: './accommodation-profile.component.html',
  styleUrls: ['./accommodation-profile.component.css']
})
export class AccommodationProfileComponent implements OnInit {

  constructor(private router: ActivatedRoute,
              private accommodationService: AccommodationService,
              private datePipe: DatePipe) { }

  id: number;

  loc: Location = {
    id: null,
    address: '',
    city: '',
    country: '',
    longitude: null,
    latitude: null
  };

  accommodation: Accommodation = {
    id: null,
    name: '',
    description: '',
    location: this.loc
  };

  accommodationUnits: AccommodationUnit[] = [];
  images = [];
  comments = [];

  page = 1;
  collectionSize = 0;
  pageSize = 0;

  ngOnInit() {
    // get accommodation id from the path
    this.router.paramMap.subscribe(
      params => {
        this.id = +params.get('id');
      });

    this.getAccommodation(this.id);
    this.getAccommodationUnits(this.id);
    this.getImages(this.id);
    this.getComments(this.id);
  }

  // get accommodation information
  getAccommodation(id: number) {
    this.accommodationService.getAccommodation(this.id).subscribe(
      data => {
        this.accommodation = data;
      },
      error => {
        alert('An error occurred while getting accommodation information.');
      }
    );
  }

  // get accommodation units of the accommodation
  getAccommodationUnits(id: number) {
    this.accommodationService.getAccommodationUnits(this.id, this.page-1).subscribe(
      data => {
        this.accommodationUnits = data['content'];
        this.collectionSize = data['totalElements'];
        this.pageSize = data['pageable'].pageSize;
      },
      error => {
        alert('An error occurred while getting accommodation units.');
      }
    );
  }

  // get accommodation images
  getImages(id: number) {
    this.accommodationService.getAccommodationImages(this.id).subscribe(
      data => {
        for (const img of data) {
          this.images.push('data:image/jpg;base64,' + window.atob(img.value));
        }
      },
      error => {
        alert('An error occurred while getting images.');
      }
    );
  }

  getComments(id: number) {
    this.accommodationService.getComments(this.id).subscribe(
      data => {
       this.comments = data;
       console.log(this.comments);
      },
      error => {
        alert('An error occurred while getting comments.');
      }
    );
  }

  onPageChange(pageNo: number){
    this.page = 0;
    this.getAccommodationUnits(this.id);
  }

  getDate(date: number) {
    return this.datePipe.transform(new Date(date), 'yyyy-MM-dd');
  }

}
