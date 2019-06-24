export interface Reservation {
  id: number;
  beginningDate: Date;
  endDate: Date;
  finalPrice: number;
  accommodationName: string;
  accommodationUnitName: string;
  checkedIn: boolean;
}
