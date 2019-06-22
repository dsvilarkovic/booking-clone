import { Component, OnInit, SystemJsNgModuleLoader } from '@angular/core';
import { Message, ReservationAccommodationInfo } from '../object-interfaces/reservation';
import { DatePipe } from '@angular/common';
import { ChatboxService } from './chatbox.service';


@Component({
  selector: 'app-chatbox',
  templateUrl: './chatbox.component.html',
  styleUrls: ['./chatbox.component.css']
})
export class ChatboxComponent implements OnInit {

  constructor(datePipe: DatePipe, private chatboxService: ChatboxService) {
  }
  messagesDone = true;
  reservationDone = false;
  // bio sam lenj, accommodation je svuda isti
  reservationList: ReservationAccommodationInfo[];

  // for messages currently opened in right panel
  messages: Message[] = [];

  activeChatAgent: any = {};
  activeRegisteredUser;
  activeReservation = { id: -1 } as ReservationAccommodationInfo; // this.reservationList[0];
  messageText = '';
  accommodationTemp;

  idIterator = 10;

  ngOnInit() {
    this.getReservations();
  }

  getReservations() {
    this.chatboxService.getReservations().subscribe(reservations => { this.reservationDone = true; this.reservationList = reservations; });
  }

  // reservationId to get exact conversation between users
  // currently not used, but should be
  getMessages(reservationId: number) {
    this.chatboxService.getMessages(reservationId).subscribe(messages => { this.messagesDone = true; this.messages = messages; });
  }

  // change parameter activeReservation, messages and activeChatAgent
  changedActiveChat(reservation: ReservationAccommodationInfo) {
    if (this.activeReservation.id !== -1 && this.activeReservation.id === reservation.id) {
      return;
    }
    this.messagesDone = false;
    this.activeReservation = reservation;
    this.getMessages(reservation.id);
    console.log(reservation);
    this.activeChatAgent.agentFirstName = reservation.agentFirstName;
    this.activeChatAgent.agentLastName = reservation.agentLastName;
    this.activeChatAgent.accommodationName = reservation.accommodationName;
    this.messageText = '';
  }
  sendMessage() {
    const message = { value: this.messageText, date: null, userId: null, id: null, reservationId: this.activeReservation.id } as Message;

    this.chatboxService.sendMessage(message).subscribe(result => {
      console.log(result);
      this.messages.push(result);
      this.getMessages(-1);
      this.messageText = '';
    });
  }



}
