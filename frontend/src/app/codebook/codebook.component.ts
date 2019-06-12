import { Component, OnInit, SystemJsNgModuleLoader } from '@angular/core';
import { Codebook, codebookTypeMap } from './codebook';
import { NgbModal, NgbTabChangeEvent } from '@ng-bootstrap/ng-bootstrap';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { CodebookService } from './codebook.service';
import { Observable } from 'rxjs';


@Component({
  selector: 'app-codebook',
  templateUrl: './codebook.component.html',
  styleUrls: ['./codebook.component.css']
})
export class CodebookComponent implements OnInit {

  // used for edit and add forms, needed for reactive-form type
  codebookForm: FormGroup;

  // page list size - full
  collectionSizeTypes: number;
  // page number
  pageTypes = 1;
  // for page size
  pageSizeTypes: number;

  // page list size - full
  collectionSizeCategory: number;
  // page number
  pageCategory = 1;
  // for page size
  pageSizeCategory: number;

  // page list size - full
  collectionSizeService: number;
  // page number
  pageService = 1;
  // for page size
  pageSizeService: number;
  // to know which list is opened
  activeList = 'accomodationType';
  constructor(private modalService: NgbModal, private service: CodebookService) {
  }


  accomodationTypeList: Codebook[] = [];
  accomodationCategoryList: Codebook[] = [];
  additionalServiceList: Codebook[] = [];

  codebookListMap = {
    accomodationType: this.accomodationTypeList,
    accomodationCategory: this.accomodationCategoryList,
    additionalService: this.additionalServiceList
  };




  ngOnInit() {
    this.getAccommodationCategories();
    this.getAccommodationTypes();
    this.getAdditionalServices();
    this.codebookForm = new FormGroup(
      {
        id: new FormControl(),
        name: new FormControl('', [Validators.required, Validators.minLength(3)]),
        codebookType: new FormControl('accomodationType', Validators.required)
      }
    );



  }

  getAccommodationTypes() {
    this.service.getAllCodeBooks(this.pageTypes - 1, 'ACCOMMODATION_TYPE').subscribe(
      types => {
        this.accomodationTypeList = (types as any).content;
        this.collectionSizeTypes = (types as any).totalElements;
        this.pageSizeTypes = (types as any).size;
      },
      error => {
        alert();
      }
    );
  }
  getAccommodationCategories() {
    this.service.getAllCodeBooks(this.pageCategory - 1, 'ACCOMMODATION_CATEGORY').subscribe(
      categories => {
        this.accomodationCategoryList = (categories as any).content;
        this.collectionSizeCategory = (categories as any).totalElements;
        this.pageSizeCategory = (categories as any).size;
      },
      error => {
        alert();
      }
    );
  }
  getAdditionalServices() {
    this.service.getAllCodeBooks(this.pageService - 1, 'ADDITIONAL_SERVICE').subscribe(
      services => {
        this.additionalServiceList = (services as any).content;
        this.collectionSizeService = (services as any).totalElements;
        this.pageSizeService = (services as any).size;
      },
      error => {
        alert();
      }
    );
  }
  // Works for both add and edit form
  openCodebookModal(content, codebook: Codebook) {
    this.codebookForm.reset();
    this.modalService.open(content, { ariaLabelledBy: 'modal-add-codebook' });

    if (codebook !== undefined) {
      this.codebookForm.value.id = codebook.id;
      this.codebookForm.value.name = codebook.name;
      this.codebookForm.setValue({
        id: codebook.id,
        name: codebook.name,
        codebookType: codebook.codebookType
      });
    }
  }

  createOrUpdateCodebook() {
    this.modalService.dismissAll();
    const codebook: Codebook = {
      id: this.codebookForm.value.id,
      name: this.codebookForm.value.name,
      codebookType: this.codebookForm.value.codebookType
    };

    if (codebook.id) {
      // if id exists, than we are updating
      // TODO PUT
      this.service.updateCodeBook(codebook, codebook.id).subscribe(
        data => {
          this.codebookForm.reset();

          switch (codebook.codebookType) {
            case 'ACCOMMODATION_TYPE': {
              const old = this.accomodationTypeList.find(x => x.id === codebook.id);
              old.name = codebook.name;
              break;
            }
            case 'ACCOMMODATION_CATEGORY': {
              const old = this.accomodationCategoryList.find(x => x.id === codebook.id);
              old.name = codebook.name;
              break;
            }
            case 'ADDITIONAL_SERVICE': {
              const old = this.additionalServiceList.find(x => x.id === codebook.id);
              old.name = codebook.name;
              break;
            }
          }
        },
        error => {
          alert(error.status);
        }
      );

    } else {
      // if not,then we push it to the list
      // TODO POST
      this.service.createCodeBook(codebook).subscribe(
        data => {
          switch (codebook.codebookType) {
            case 'ACCOMMODATION_TYPE': {
              this.accomodationTypeList.push(data as Codebook);
              break;
            }
            case 'ACCOMMODATION_CATEGORY': {
              this.accomodationCategoryList.push(data as Codebook);
              break;
            }
            case 'ADDITIONAL_SERVICE': {
              this.additionalServiceList.push(data as Codebook);
              break;
            }
          }
        },
        error => {
          alert(error.status);
        }
      );
    }
  }

  removeCodebook(removedCodebook) {
    switch (removedCodebook.codebookType) {
      case 'ACCOMMODATION_TYPE': {
        this.service.removeCodeBook(removedCodebook.id, removedCodebook.codebookType).subscribe(
          data => {
            this.accomodationTypeList = this.accomodationTypeList.filter(codebook => codebook.id !== removedCodebook.id);
          },
          error => {
            alert(error.status);
          }
        );
        break;
      }
      case 'ACCOMMODATION_CATEGORY': {
        this.service.removeCodeBook(removedCodebook.id, removedCodebook.codebookType).subscribe(
          data => {
            this.accomodationCategoryList = this.accomodationCategoryList.filter(codebook => codebook !== removedCodebook);
          },
          error => {
            alert(error.status);
          }
        );
        break;
      }
      case 'ADDITIONAL_SERVICE': {
        this.service.removeCodeBook(removedCodebook.id, removedCodebook.codebookType).subscribe(
          data => {
            this.additionalServiceList = this.additionalServiceList.filter(codebook => codebook !== removedCodebook);
          },
          error => {
            alert(error.status);
          }
        );
        break;
      }
    }
  }

  tabChange($event: NgbTabChangeEvent) {
    // TODO import list for list needed

  }

  onPageChangeTypes(pageNo: number) {
    // TODO add something
    this.pageTypes = pageNo;
    this.getAccommodationTypes();
  }

  onPageChangeCategory(pageNo: number) {
    // TODO add something here
    this.pageCategory = pageNo;
    this.getAccommodationCategories();
  }

  onPageChangeService(pageNo: number) {
    // TODO add something here
    this.pageService = pageNo;
    this.getAdditionalServices();
  }
}

