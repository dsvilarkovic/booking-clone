import { Component, OnInit } from '@angular/core';
import { Codebook, codebookTypeMap} from './codebook';
import { NgbModal, NgbTabChangeEvent} from '@ng-bootstrap/ng-bootstrap';
import { FormGroup, FormControl, Validators } from '@angular/forms';


@Component({
  selector: 'app-codebook',
  templateUrl: './codebook.component.html',
  styleUrls: ['./codebook.component.css']
})
export class CodebookComponent implements OnInit {

  constructor(private modalService: NgbModal) { }


  accomodationTypeList: Codebook[] = [
    {id : 1, name: 'Prvi tip smestaja', codebookType: 'accomodationType'},
    {id : 2, name: 'Drugi tip smestaja', codebookType: 'accomodationType'},
    {id : 3, name: 'Treci tip smestaja', codebookType: 'accomodationType'}
  ];

  accomodationCategoryList: Codebook[] = [
    {id : 1, name: 'Prva kategorija', codebookType: 'accomodationCategory'},
    {id : 2, name: 'Druga kategorija', codebookType: 'accomodationCategory'},
    {id : 3, name: 'Treca kategorija', codebookType: 'accomodationCategory'}
  ];

  additionalServiceList: Codebook[] = [
    {id : 1, name: 'Prva dodatna usluga', codebookType: 'additionalService'},
    {id : 2, name: 'Druga dodatna usluga', codebookType: 'additionalService'},
    {id : 3, name: 'Treca dodatna usluga', codebookType: 'additionalService'}
  ];

  codebookListMap = {
    accomodationType : this.accomodationTypeList,
    accomodationCategory : this.accomodationCategoryList,
    additionalService : this.accomodationTypeList
  };
  // used for edit and add forms, needed for reactive-form type
  codebookForm: FormGroup;

  // page list size - full
  collectionSize = 3;
  // page number
  page = 1;
  // for page size
  pageSize = 7;
  // to know which list is opened
  activeList = 'accomodationType';



  ngOnInit() {
    this.codebookForm = new FormGroup(
      {
      id : new FormControl(),
      name: new FormControl('', [Validators.required , Validators.minLength(3)]),
      codebookType: new FormControl('accomodationType', Validators.required)
      }
    );


    console.log(this.codebookListMap);
  }

  // Works for both add and edit form
  openCodebookModal(content, codebook: Codebook) {
      this.modalService.open(content, {ariaLabelledBy: 'modal-add-codebook'});

      if (codebook !== undefined) {
        console.log(content);
        console.log(codebook.name);
        console.log(codebook.codebookType);

        this.codebookForm.setValue({
            id : codebook.id,
            name : codebook.name,
            codebookType : codebook.codebookType
        });
      }
  }

  createOrUpdateCodebook() {
     const codebook: Codebook = {
       id: this.codebookForm.value.id,
       name: this.codebookForm.value.name,
       codebookType : this.codebookForm.value.codebookType
     };


     if (codebook.id) {
        // if id exists, than we are updating
        // TODO PUT
     } else {
        // if not,then we push it to the list
        // TODO POST
     }
  }

  removeCodebook(removedCodebook) {
      switch (removedCodebook.codebookType) {
        case 'accomodationType': {
          console.log('OVdi');
          this.accomodationTypeList = this.accomodationTypeList.filter(codebook => codebook.id !== removedCodebook.id);
          console.log(removedCodebook);
          break;
        }
        case 'accomodationType': {
          this.accomodationCategoryList = this.accomodationCategoryList.filter(codebook => codebook !== removedCodebook);
          break;
        }
        case 'accomodationType': {
          this.additionalServiceList = this.additionalServiceList.filter(codebook => codebook !== removedCodebook);
          break;
        }
      }
  }

  tabChange($event: NgbTabChangeEvent) {
    // TODO import list for list needed
    console.log($event.nextId);
  }

  onPageChange(pageNo: number) {
    // TODO add something here
  }
}

