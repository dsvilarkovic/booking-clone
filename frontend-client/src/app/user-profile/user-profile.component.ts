import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { User } from './user';
import { NgbTabChangeEvent } from '@ng-bootstrap/ng-bootstrap';
import { Reservation } from './reservation';
import { DatePipe } from '@angular/common';


@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  id: number;
  // user : User;

  user: User = {
    id: 0,
    firstName: 'Marijana',
    lastName: 'Kološnjaji',
    email: 'majak96@gmail.com',
    address: 'Trg Dositeja Obradovića 6'
  };

  currentReservations: Reservation[] = [
    {
      id: 0, beginning_date: new Date('2019-04-27'), end_date: new Date('2019-05-01 13:13:00'),
      hotel: 'Sheraton', room: 'Master Suite', final_price: 510
    },
    {
      id: 1, beginning_date: new Date('2019-04-27'), end_date: new Date('2019-05-01 13:13:00'),
      hotel: 'Sheraton', room: 'Single room', final_price: 2510
    }
  ];

  pastReservations: Reservation[] = [
    {
      id: 2, beginning_date: new Date('2019-04-27'), end_date: new Date('2019-05-01 13:13:00'),
      hotel: 'Sheraton', room: 'Master Suite', final_price: 510
    },
    {
      id: 3, beginning_date: new Date('2019-04-27'), end_date: new Date('2019-05-01 13:13:00'),
      hotel: 'Sheraton', room: 'Single room', final_price: 2510
    }
  ];

  userImage = 'https://cdn0.iconfinder.com/data/icons/user-pictures/100/malecostume-512.png';

  constructor(private router: ActivatedRoute,
              private datePipe: DatePipe) { }

  ngOnInit() {
    // get user id from the path
    this.router.paramMap.subscribe(
      params => {
        this.id = +params.get('id');
      });

    // getUser()
    // getReservations()
  }


  getUser() {
    /* this.userService.getUser(this.id).subscribe(
      data => {
        this.user = data;
      },
      error => {
        console.log('There was an error.');
      }
    ); */
  }

  getReservations(){
        /* this.userService.getReservations(this.id).subscribe(
      data => {
        //todo: filtrirati po datumu rezervacije!
        this.currentReservations = data;
        this.pastReservations = data;
      },
      error => {
        console.log('There was an error.');
      }
    ); */
  }

  tabChange($event: NgbTabChangeEvent) {
    // TODO import list for list needed
    console.log($event.nextId);
  }
}
