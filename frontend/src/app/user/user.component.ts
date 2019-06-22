import { Component, OnInit } from '@angular/core';
import { NgbTabChangeEvent, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { User } from './user';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { UserService } from './user.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  activeTab: string;

  regUsersList: User[] = [];
  adminsList: User[] = [];
  agentsList: User[] = [];


  collectionSize = 0;
  page = 1;
  pageSize = 10;

  userForm: FormGroup;

  constructor(private modalService: NgbModal, private userService: UserService) { }

  ngOnInit() {
    this.activeTab = 'reg-users-tab';
    this.getUsersByType('registered', this.page - 1);
  }

  tabChange($event: NgbTabChangeEvent) {
    this.activeTab = $event.nextId;

    if (this.activeTab === 'admins-tab') {
      this.getUsersByType('admin', this.page - 1);
      this.userForm = new FormGroup(
        {
          firstName: new FormControl('', Validators.required),
          lastName: new FormControl('', Validators.required),
          email: new FormControl('', [Validators.required, Validators.email])
        }
      );
    } else if (this.activeTab === 'agents-tab') {
      this.getUsersByType('agent', this.page - 1);
      this.userForm = new FormGroup(
        {
          firstName: new FormControl('', Validators.required),
          lastName: new FormControl('', Validators.required),
          address: new FormControl('', Validators.required),
          email: new FormControl('', [Validators.required, Validators.email]),
          pib: new FormControl('', [Validators.required, Validators.minLength(9), Validators.maxLength(9)])
        }
      );
    } else {
      this.getUsersByType('registered', this.page - 1);

    }
  }

  onPageChange(pageNo) {
    this.page = pageNo + 1;

    if (this.activeTab === 'agents-tab') {
      this.getUsersByType('agent', this.page - 1);
    } else if(this.activeTab === 'admins-tab') {
      this.getUsersByType('admin', this.page - 1);
    } else {
      this.getUsersByType('registered', this.page - 1);
    }
  }

  userActivation(user: User) {
    this.userService.changeUserActivation(user).subscribe(
      data => {
        user.activated = !user.activated;
      },
      error => {
        alert('Error while changing the user\'s information');
      }
    );
  }

  deleteUser(user) {
    this.userService.removeUser(user.id).subscribe(
      data => {
        this.getUsersByType('registered', this.page - 1);
      },
      error => {
        alert('Error while deleting the user');
      }
    );
  }

  createUser() {
    if (this.activeTab === 'agents-tab') {
      const agent: User = {
        id: null,
        firstName: this.userForm.value.firstName,
        lastName: this.userForm.value.lastName,
        address: this.userForm.value.address,
        pib: this.userForm.value.pib,
        email: this.userForm.value.email,
        userType: 'agent',
        activated: true

      };
      this.userService.createUser(agent, 'agent').subscribe(
        data => {
          this.modalService.dismissAll();
          this.userForm.reset();
          this.getUsersByType('agent', this.page);
        },
        error => {
          alert('Error while creating the agent');
        }
      );
    } else if (this.activeTab === 'admins-tab') {
      const admin: User = {
        id: null,
        firstName: this.userForm.value.firstName,
        lastName: this.userForm.value.lastName,
        address: '',
        pib : null,
        email: this.userForm.value.email,
        userType: 'admin',
        activated: true

      };
      this.userService.createUser(admin, 'admin').subscribe(
        data => {
          this.modalService.dismissAll();
          this.userForm.reset();
          this.getUsersByType('admin', this.page);
        },
        error => {
          alert('Error while creating the administrator');
        }
      );
    }
  }

  openModal(content) {
    this.modalService.open(content, { ariaLabelledBy: 'modal-add-users' });
  }

  getUsersByType(type: string, page: number){
    this.userService.getUsersByType(type, page).subscribe(
      data => {
        if (type === 'registered') {
          this.regUsersList = data['content'];
        } else if (type === 'admin') {
          this.adminsList = data['content'];
        } else {
          this.agentsList = data['content'];
        }

        this.collectionSize = data['totalElements'];
        this.pageSize = data['pageable']['pageSize'];
      },
      error => {
        console.log('Error');
      }
    );
  }

}
