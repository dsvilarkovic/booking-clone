import { AccommodationUnit } from '../accommodation-profile/accommodationunit';
import { User } from '../object-interfaces/user';

export class Reservation {
    id: number;
    beginningDate: number;
    endDate: number;
    finalPrice: number;
    checkedIn: boolean;
    user: User;
    accommodationUnit: AccommodationUnit;
}
