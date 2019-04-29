import { User } from './user';

export class Accommodation {
    id: number;
    // image: string; // base64 convert needed, only one image for representation in search results
    name: string;
    capacity: number;
    description: string;
    defaultPrice: number;
    cancelationPeriod: number;
    accommodationCategory: string;
    accommodationType: string;
    additionalService: string[];
    // location skip
    user: User;
    rating: number;
}
