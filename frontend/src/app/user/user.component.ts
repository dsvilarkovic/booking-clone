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

  // za sada liste ovako kao primer
  regUsersList: User[] = [
    {
      id: 0, firstName: 'Nesto', lastName: 'Nesto', address: 'Neka', PIB: null,
      active: true, email: 'neki@gmail.com', userType: 'regUser'
    },
    {
      id: 1, firstName: 'Nesto', lastName: 'Nesto', address: 'Neka', PIB: null,
      active: false, email: 'neki@gmail.com', userType: 'regUser'
    },
    {
      id: 2, firstName: 'Nesto', lastName: 'Nesto', address: 'Neka', PIB: null,
      active: true, email: 'neki@gmail.com', userType: 'regUser'
    },
  ];
  adminsList: User[] = [
    {
      id: 0, firstName: 'Nesto', lastName: 'Nesto', address: null, PIB: null,
      active: true, email: 'neki@gmail.com', userType: 'admin'
    },
    {
      id: 1, firstName: 'Nesto', lastName: 'Nesto', address: null, PIB: null,
      active: true, email: 'neki@gmail.com', userType: 'admin'
    },
    {
      id: 2, firstName: 'Nesto', lastName: 'Nesto', address: null, PIB: null,
      active: true, email: 'neki@gmail.com', userType: 'admin'
    },
  ];
  agentsList: User[] = [
    {
      id: 0, firstName: 'Nesto', lastName: 'Nesto', address: 'Neka', PIB: 987654321,
      active: true, email: 'neki@gmail.com', userType: 'agent'
    },
    {
      id: 1, firstName: 'Nesto', lastName: 'Nesto', address: 'Neka', PIB: 123456789,
      active: true, email: 'neki@gmail.com', userType: 'agent'
    },
    {
      id: 2, firstName: 'Nesto', lastName: 'Nesto', address: 'Neka', PIB: 123456789,
      active: true, email: 'neki@gmail.com', userType: 'agent'
    },
  ];


  collectionSize = 3;
  page = 1;
  pageSize = 7;

  userForm: FormGroup;

  constructor(private modalService: NgbModal, private userService: UserService) { }

  ngOnInit() {
    this.activeTab = 'reg-users-tab';
    /*
    this.userService.getUsersByType('regUsers').subscribe(
      data => {
        this.regUsersList = data;
      },
      error => {
        alert('Error');
      }
    );
    */
  }

  tabChange($event: NgbTabChangeEvent) {
    this.activeTab = $event.nextId;

    if (this.activeTab === 'admins-tab') {
      /*
      this.userService.getUsersByType('admins').subscribe(
        data => {
          this.agentsList = data;
        },
        error => {
          alert('Error');
        }
      );
      */
      this.userForm = new FormGroup(
        {
          firstName: new FormControl('', Validators.required),
          lastName: new FormControl('', Validators.required),
          email: new FormControl('', [Validators.required, Validators.email])
        }
      );
    } else if (this.activeTab === 'agents-tab') {
      /*
      this.userService.getUsersByType('agents').subscribe(
        data => {
          this.adminsList = data;
        },
        error => {
          alert('Error');
        }
      );
      */
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
      /*
      this.userService.getUsersByType('regUsers').subscribe(
        data => {
          this.regUsersList = data;
        },
        error => {
          alert('Error');
        }
      );
      */
    }
  }

  onPageChange(pageNo) {
    this.page = pageNo + 1;
  }

  userActivation(user: User) {
    user.active = !user.active;
    this.userService.changeUserActivation(user).subscribe(
      data => {
      },
      error => {
        alert('Error while changing the user\'s information');
      }
    );
  }

  deleteUser(user) {
    this.userService.removeUser(user.id).subscribe(
      data => {
      },
      error => {
        alert('Error while deleting the user');
      }
    );
  }

  createUser() {
    if (this.activeTab === 'agents-tab') {
      const agent: User = {
        id: -1,
        firstName: this.userForm.value.firstName,
        lastName: this.userForm.value.lastName,
        address: this.userForm.value.address,
        PIB: this.userForm.value.pib,
        email: this.userForm.value.email,
        userType: 'AGENT',
        active: true

      };
      this.userService.createUser(user).subscribe(
        data => {
        },
        error => {
          alert('Error while creating the agent');
        }
      );
    } else if (this.activeTab === 'admins-tab') {
      const agent: User = {
        id: -1,
        firstName: this.userForm.value.firstName,
        lastName: this.userForm.value.lastName,
        address: '',
        PIB: null,
        email: this.userForm.value.email,
        userType: 'ADMIN',
        active: true

      };
      this.userService.createUser(user).subscribe(
        data => {
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

}
