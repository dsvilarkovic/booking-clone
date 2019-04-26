import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';

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

}
