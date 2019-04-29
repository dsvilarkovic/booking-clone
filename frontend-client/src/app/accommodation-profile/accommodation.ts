import { Location } from './location';

export interface Accommodation {
  id: number;
  name: string;
  description: string;
  rating: number;
  location: Location;
}
