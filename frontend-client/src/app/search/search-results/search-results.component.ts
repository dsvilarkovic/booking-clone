import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Accommodation } from 'src/app/object-interfaces/accommodation';
import { ActivatedRoute } from '@angular/router';
import { SearchService } from '../search.service';
import { AccommodationUnit } from 'src/app/accommodation-profile/accommodationunit';
import { AccommodationCategory } from '../model/accommodationcategory';
import { AccommodationType } from '../model/accommodationtype';
import { routerNgProbeToken } from '@angular/router/src/router_module';
import { Router } from '@angular/router';

@Component({
  selector: 'app-search-results',
  templateUrl: './search-results.component.html',
  styleUrls: ['./search-results.component.css']
})
export class SearchResultsComponent implements OnInit {

  constructor(private route: ActivatedRoute,
              private searchService: SearchService, private router: Router) { }

  searchObject = {
    location: null,
    beginningDate: null,
    endDate: null,
    numberOfPersons: null
  };

  dropdownSettings = {};
  sort = 'none';

  categoriesList = [];
  accTypeList = [];
  additionalServicesList = [];
  accommodationUnitList: AccommodationUnit[] = [];

  filterForm: any = {};

  /*accommodationUnitList: AccommodationUnit[] = [
    {
      id: 0,
      name: 'Master Suite',
      capacity: 2,
      cancelationPeriod : null,
      defaultPrice : 200,
      image : null
    },
    {
      id: 1,
      name: 'Double room',
      capacity: 2,
      cancelationPeriod : null,
      defaultPrice : 300,
      image : null
    },
    {
      id: 2,
      name: 'Single room',
      capacity: 1,
      cancelationPeriod : null,
      defaultPrice : 400,
      image : null
    },
  ];
  */
  

  collectionSize = 4;
  page = 1;
  pageSize = 5;

  ngOnInit() {
    // preuzimanje parametara iz putanje
    this.route.queryParams
      .subscribe(params => {
        this.searchObject.location = params.location;
        console.log(this.searchObject.location);

        this.searchObject.beginningDate = params.beginningDate;
        this.searchObject.endDate = params.endDate;
        this.searchObject.numberOfPersons = params.numberOfPersons;
      });

    // rezultati pretrage
    this.getNormalSearchResults(0);

    // popunjavanje lista za filtraciju
    this.getAccomodationTypes();
    this.getCategories();
    this.getAdditonalServices();

    this.dropdownSettings = {
      singleSelection: false,
      idField: 'id',
      textField: 'name',
      selectAllText: 'Select All',
      unSelectAllText: 'UnSelect All',
      itemsShowLimit: 3,
      allowSearchFilter: true
    };
  }

  getAdditonalServices() {
    this.searchService.getAdditionalServices().subscribe(
      data => {
        this.additionalServicesList = data;
      },
      error => {
        console.log('error');
      }
    );
  }

  getCategories() {
    this.searchService.getAccommodationCategories().subscribe(
      data => {
        this.categoriesList = data;
      },
      error => {
        console.log('error');
      }
    );
  }

  getAccomodationTypes() {
    this.searchService.getAccommodationTypes().subscribe(
      data => {
        this.accTypeList = data;
      },
      error => {
        console.log('error');
      }
    );
  }

  onItemSelect(item: any) {
    console.log(item);
  }
  onSelectAll(items: any) {
    console.log(items);
  }

  filterFormSubmit() {
    console.log(this.filterForm.additionalServicesSelected);
  }

  onPageChange(pageNo: number) {
    this.getNormalSearchResults(pageNo - 1);
  }

  // accommodationUnit: AccommodationUnit = {
  //   id : 1,
  //   cancelationPeriod : 5,
  //   capacity : 10,
  //   defaultPrice : 100,
  //   name : 'Luxury Suite',
  //   image : null
  // };
  reserveCheckout(accommodationUnit: AccommodationUnit) {
    // TODO: Dusan insert login
    sessionStorage.setItem('accommodationUnit', JSON.stringify(accommodationUnit));
    this.router.navigateByUrl('/reservation-checkout');
  }

  getNormalSearchResults(page: number) {
    this.searchService.normalSearch(this.searchObject.location, this.searchObject.beginningDate,
      this.searchObject.endDate, this.searchObject.numberOfPersons, page).subscribe(
        data => {
          this.accommodationUnitList = data['content'];
          this.collectionSize = data['totalElements'];
          this.pageSize = data['pageable'].pageSize;
          this.page = data['pageable'].pageNumber + 1;
        },
        error => {
          console.log('error!');
        }
    );
  }

  getAdvancedSearchResults(page: number) {

  }


}
