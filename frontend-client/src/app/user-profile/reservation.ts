export interface Reservation {
  id: number;
  beginning_date: Date;
  end_date: Date;
  final_price: number;
  hotel: string;
  room: string;
}
