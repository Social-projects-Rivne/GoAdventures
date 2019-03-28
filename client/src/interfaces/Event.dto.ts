import { GalleryDto } from './Gallery.dto';
import { UserDto } from './User.dto';

export interface EventDto {
  id: number;
  topic: string;
  location: string;
  description: string;
  startDate: string;
  endDate: string;
  participants: UserDto[];
  gallery: GalleryDto;
}
