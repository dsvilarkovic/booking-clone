import { Component, OnInit } from '@angular/core';
import { Reservation, Message, ReservationAccommodation} from '../object-interfaces/reservation';
import { User } from '../object-interfaces/user';
import { DatePipe } from '@angular/common';


@Component({
  selector: 'app-chatbox',
  templateUrl: './chatbox.component.html',
  styleUrls: ['./chatbox.component.css']
})
export class ChatboxComponent implements OnInit {

  constructor(datePipe: DatePipe) { }

  agentUser: User = {
    id: 1,
    first_name: 'Zivojin',
    last_name: 'Zivot',
    email: 'dda@da.com',
    address: 'Nesvrstanih 121',
    password: '',
    pib: 100000000000000,
    userType: 'agent'
  };

  registeredUser: User = {
    id: 2,
    first_name: 'Milana',
    last_name: 'Voditelj',
    email: undefined,
    address: 'Ulicnih ulica 4',
    password: '',
    pib: -1,
    userType: 'registered'
  };

  reservationAccommodation: ReservationAccommodation = {
    id: 1,
    name: 'Hotel JetSet na Klisi',
    agent: this.agentUser
  };

  // bio sam lenj, accommodation je svuda isti
  reservationList: Reservation[] = [
    {
      id: 1,
      beginningDate: new Date('2019-01-16'),
      endDate: new Date(),
      finalPrice: 1000,
      checkedIn: true,
      rating: undefined,
      messageCount: 5,
      comment: undefined,
      user: this.registeredUser,
      accommodation: this.reservationAccommodation
    },
    {
      id: 2,
      beginningDate: new Date('2019-05-16'),
      endDate: new Date(),
      finalPrice: 140,
      checkedIn: true,
      rating: undefined,
      messageCount: 2,
      comment: undefined,
      user: this.registeredUser,
      accommodation: this.reservationAccommodation
    },
    {
      id: 3,
      beginningDate: new Date('2019-03-16'),
      endDate: new Date(),
      finalPrice: 300,
      checkedIn: true,
      rating: undefined,
      messageCount: 13,
      comment: undefined,
      user: this.registeredUser,
      accommodation: this.reservationAccommodation
    },
  ];

  // for messages currently opened in right panel
  messages: Message[] = [
    {
      id: 1,
      date: new Date(),
      user: this.agentUser,
      value: 'Zdravo'
    },
    {
      id: 1,
      date: new Date(),
      user: this.registeredUser,
      value: 'Sta znaci Zivojin?'
    },
    {
      id: 1,
      date: new Date(),
      user: this.agentUser,
      value: 'Glupa fora'
    },
  ];

  activeChatAgent = this.agentUser;
  activeReservation = this.reservationList[0];
  messageText = 'Jaidjiajiji';

  ngOnInit() {  }

  // change parameter activeReservation, messages and activeChatAgent
  changedActiveChat(reservation: Reservation) {
      console.log(reservation.id);
  }
  sendMessage() {
    console.log(this.messageText);
    let message = {id: -1, value: this.messageText, date: new Date(), user: this.registeredUser} as Message;
    console.log('alooo');
    this.messages.push(message);


    this.messageText = '';
  }

}
