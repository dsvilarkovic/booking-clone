import { Component, OnInit } from '@angular/core';
import { Reservation, Message} from '../object-interfaces/reservation';
import { User } from '../object-interfaces/user';
import { DatePipe } from '@angular/common';
import { ChatboxService } from './chatbox.service';


@Component({
  selector: 'app-chatbox',
  templateUrl: './chatbox.component.html',
  styleUrls: ['./chatbox.component.css']
})
export class ChatboxComponent implements OnInit {

  constructor(datePipe: DatePipe, private chatboxService: ChatboxService) { }

  // bio sam lenj, accommodation je svuda isti
  reservationList: Reservation[];

  // for messages currently opened in right panel
  messages: Message[] = [];

  activeChatAgent;
  activeRegisteredUser;
  activeReservation = {id: -1} as Reservation; // this.reservationList[0];
  messageText = '';

  idIterator = 10;

  ngOnInit() {
      this.getReservations();
   }

  getReservations() {
    this.chatboxService.getReservations().subscribe(reservations => this.reservationList = reservations);
  }

  // reservationId to get exact conversation between users
  // currently not used, but should be
  getMessages(reservationId: number) {
    this.chatboxService.getMessages().subscribe(messages => this.messages = messages);
  }

  // change parameter activeReservation, messages and activeChatAgent
  changedActiveChat(reservation: Reservation) {
      if (this.activeReservation.id !== -1 && this.activeReservation.id === reservation.id) {
          return;
      }

      this.activeReservation = reservation;
      this.activeChatAgent = reservation.accommodation.agent;
      this.getMessages(reservation.id);
      this.messageText = '';
  }
  sendMessage() {

    this.activeRegisteredUser = {
          id: 17,
          firstName: 'Dusan',
          lastName: 'Dusanovic',
          address: '',
          email: '',
          password: '',
          pib : -1,
          userType: 'registered'
    } as User;

    const message = {value: this.messageText, date: new Date(), user: this.activeRegisteredUser} as Message;

    this.chatboxService.sendMessage(message).subscribe(result => {
      this.getMessages(-1);
      this.messageText = '';
    });
  }

}
