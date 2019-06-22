import {User} from './user';

export class Reservation {
    id: number;
    beginningDate: Date;
    endDate: Date;
    finalPrice: number;
    checkedIn: boolean;
    ratingId: number;
    // messages ==> dto needed for this, no need for list of messages
    messageCount: number; // only here
    commentId: number;
    userId: number;
    accommodationUnitId: number;
    numberOfPersons: number;

}

export class ReservationAccommodationInfo {
    id: number;
    beginningDate: Date;
    endDate: Date;
    finalPrice: number;
    checkedIn: boolean;
    ratingId: number;
    // messages ==> dto needed for this, no need for list of messages
    messageCount: number; // only here
    commentId: number;
    userId: number;
    accommodationUnitId: number;
    numberOfPersons: number;
    accommodationUnitName: number;
    agentFirstName: string;
    agentLastName: string;
    accommodationName: string;
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
    date: number;
    userId: number;
    userType: string;
    reservationId: number;
}

export class Comment {
    id: number;
    value: string;
    user: User;
}
