import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Accommodation } from 'src/app/object-interfaces/accommodation';

@Component({
  selector: 'app-search-results',
  templateUrl: './search-results.component.html',
  styleUrls: ['./search-results.component.css']
})
export class SearchResultsComponent implements OnInit {

  constructor() { }
  additionalServicesList = [];
  dropdownSettings = {};
  sort = 'none';
  categoriesList = [];
  accTypeList = [];
  filterForm: any = {};
  accommodationResultList: Accommodation[] = [
    {
      id: 1,
      name: "Hotel Park",
      accommodationCategory: "Uncategorized",
      accommodationType: "Hotel", 
      additionalService: ["WI-FI", "Parking"],
      cancelationPeriod: undefined,
      capacity: 10,
      defaultPrice: 200,
      description: "Round mound of rebound",
      user: undefined,
      rating: 2.5
    },
    {
      id: 2,
      name: "Hotel Master",
      accommodationCategory: "5 stars",
      accommodationType: "Hotel", 
      additionalService: ["WI-FI", "TV"],
      cancelationPeriod: undefined,
      capacity: 4,
      defaultPrice: 200,
      description: "Fantastic, majestic, great excellent",
      user: undefined,
      rating: 3
    },
    {
      id: 3,
      name: "FTN Apartmani",
      accommodationCategory: "Uncategorized",
      accommodationType: "Apartments", 
      additionalService: ["WI-FI", "Parking"],
      cancelationPeriod: undefined,
      capacity: 666,
      defaultPrice: 404,
      description: "Look at my look shoes, aaaand look at me lucky suiteee.",
      user: undefined,
      rating: 4
    },
    {
      id: 4,
      name: "Motel Bosna",
      accommodationCategory: "Uncategorized",
      accommodationType: "Motel", 
      additionalService: ["TV", "Parking", "Shower", "4G"],
      cancelationPeriod: undefined,
      capacity: 2,
      defaultPrice: 3200,
      description: "Ozbiljan hotel, za galantne goste.",
      user: undefined,
      rating: 4.6
    },
  ];

  collectionSize = 4;
  page = 1;
  pageSize = 5;

  ngOnInit() {

    this.getAccomodationTypes();
    this.getCategories();
    this.getAdditonalServices();

    this.dropdownSettings = {
      singleSelection: false,
      idField: 'item_id',
      textField: 'item_text',
      selectAllText: 'Select All',
      unSelectAllText: 'UnSelect All',
      itemsShowLimit: 3,
      allowSearchFilter: true
    };
  }

  getAdditonalServices() {
    // TODO 0: preuzimanje definisanih servisa
    this.additionalServicesList = [
      { item_id: 1, item_text: 'WI-FI' },
      { item_id: 2, item_text: 'Parking' },
      { item_id: 3, item_text: 'All inclusive' },
      { item_id: 4, item_text: 'TV' }
    ];
  }

  getCategories() {
    // TODO 1: preuzimanje definisanih kategorija
    this.categoriesList = ['None', '1 star'];
  }

  getAccomodationTypes() {
    // TODO 2: preuzimanje definisanih kategorija
    this.accTypeList = ['Hotel', 'Bed&breakfast', 'Apartman'];
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
    // TODO: Dusan insert something
  }

}
