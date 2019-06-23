import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SearchService } from '../search.service';
import { AccommodationUnit } from 'src/app/accommodation-profile/accommodationunit';
import { Router } from '@angular/router';
import { TokenStorageService } from 'src/app/token-storage.service';
import { LoginFormComponent } from 'src/app/login-form/login-form.component';
import { Reservation } from 'src/app/reservation-checkout/reservation';
import { NgbModal, NgbModalOptions } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-search-results',
  templateUrl: './search-results.component.html',
  styleUrls: ['./search-results.component.css']
})
export class SearchResultsComponent implements OnInit {

  constructor(private route: ActivatedRoute,
              private searchService: SearchService,
              private router: Router,
              private tokenStorage: TokenStorageService,
              private modalService: NgbModal ) { }

  searchObject = {
    location: null,
    beginningDate: null,
    endDate: null,
    numberOfPersons: null
  };

  modalOption: NgbModalOptions = {};

  dropdownSettings = {};
  sort = 'none';

  categoriesList = [];
  accTypeList = [];
  additionalServicesList = [];
  accommodationUnitList: AccommodationUnit[] = [];

  filterForm: any = {};

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

  reserveCheckout(accommodationUnitParam: AccommodationUnit) {
    if (this.tokenStorage.getUser() == null) {
      this.modalService.open(LoginFormComponent, this.modalOption);
    } else {
      const reservation: Reservation = {
        id: null,
        accommodationUnit: accommodationUnitParam,
        beginningDate: new Date(this.searchObject.beginningDate).getTime(),
        endDate: new Date(this.searchObject.endDate).getTime(),
        checkedIn: false,
        finalPrice: 0,
        userId: null,
        numberOfPersons: this.searchObject.numberOfPersons
      };
      sessionStorage.setItem('reservation', JSON.stringify(reservation));
      this.router.navigateByUrl('/reservation-checkout');
    }

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

