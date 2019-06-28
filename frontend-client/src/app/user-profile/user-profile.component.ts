import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { User } from './user';
import { NgbTabChangeEvent, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Reservation } from './reservation';
import { DatePipe } from '@angular/common';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { UserService } from './user.service';


@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  constructor(private router: ActivatedRoute,
              private modalService: NgbModal,
              private datePipe: DatePipe,
              private userService: UserService) { }

  commentForm: FormGroup;
  ratingForm: FormGroup;


  currentReservations: Reservation[] = [];
  pastReservations: Reservation[] = [];

  user: User = {
    id: null,
    firstName: '',
    lastName: '',
    address: '',
    email: ''
  };

  userImage = 'https://cdn0.iconfinder.com/data/icons/user-pictures/100/malecostume-512.png';

  ngOnInit() {
    this.getUser();
    this.getReservations();

    this.commentForm = new FormGroup(
      {
        comment: new FormControl('', [Validators.required, Validators.minLength(3)]),
      }
    );

    this.ratingForm = new FormGroup(
      {
        rating: new FormControl('', [Validators.required]),
      }
    );
  }


  // get user information
  getUser() {
    this.userService.getUser().subscribe(
      data => {
        this.user = data;
      },
      error => {
        alert('An error occurred while getting user information.');
      }
    );
  }

  // get user reservations
  getReservations() {
    this.userService.getReservations().subscribe(
      data => {
        console.log(data);
        for (const res of data) {
          if (res.checkedIn === true) {
            this.pastReservations.push(res);
          } else {
            this.currentReservations.push(res);
          }
        }
      },
      error => {
        alert('An error ocurred while getting user reservations.');
      }
    );
  }

  postComment() {
    // do something


  }

  postRating() {
    // do something


  }

  tabChange($event: NgbTabChangeEvent) {
    console.log($event.nextId);
  }

  // open modal dialog to write a message
  open1(content1, reservation: Reservation) {
    // do something

    this.modalService.open(content1);
  }

  // open modal dialog to rate
  open2(content2, reservation: Reservation) {
    // do something

    this.modalService.open(content2);
  }

  cancelReservation(reservation) {
    this.userService.cancelReservation(reservation.id).subscribe(
      data => {
        this.currentReservations = this.currentReservations.filter(r => r !== reservation);
      }, error => {
        console.log(error.error);
        alert(error.error);
      }
    );
  }
}
