import {User} from './user';

export class Reservation {
    id: number;
    beginningDate: Date;
    endDate: Date;
    finalPrice: number;
    checkedIn: boolean;
    rating: Rating;
    // messages ==> dto needed for this, no need for list of messages
    messageCount: number; // only here
    comment: Comment;
    user: User;
    accommodation: ReservationAccommodation;

}

// only for reservation
export class ReservationAccommodation {
    id: number;
    name: string;
    agent: User;
}

export class Rating {
    id: number;
    value: number;
}

export class Message {
    id: number;
    value: string;
    date: Date;
    user: User;
}

export class Comment {
    id: number;
    value: string;
    user: User;
}
