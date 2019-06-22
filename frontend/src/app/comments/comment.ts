import { User } from '../user/user';

export interface Comment {
  id: number;
  value: string;
  user: User;
  commentState: string;
  date: number;
}
