import { AccommodationUnit } from '../accommodation-profile/accommodationunit';
import { User } from '../object-interfaces/user';


export class Reservation {
    id: number;
    beginningDate: number;
    endDate: number;
    finalPrice: number;
    checkedIn: boolean;
    userId: number;
    accommodationUnit: AccommodationUnit;
    numberOfPersons: number;
}


export class ReservationDTO {
    id: number;
    beginningDate: number;
    endDate: number;
    finalPrice: number;
    checkedIn: boolean;
    userId: number;
    accommodationUnitId: number;
    numberOfPersons: number;
    ratingId: number;
    commentId: number;
}
