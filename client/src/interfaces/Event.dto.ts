import { GalleryDto } from './Gallery.dto';
import { UserDto } from './User.dto';

export interface EventDto {
  id: number | undefined;
  topic: string | undefined;
  location: string | undefined;
  latitude: number;
  longitude: number;
  description: string | undefined;
  startDate: string | undefined;
  endDate: string | undefined;
  category: string | undefined;
  participants: UserDto[] | undefined;
  gallery: GalleryDto | undefined;
}
