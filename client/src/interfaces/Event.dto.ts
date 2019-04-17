import { GalleryDto } from './Gallery.dto';
import { UserDto } from './User.dto';

export interface EventDto {
  id: number;
  topic: string | undefined;
  location: string | undefined;
  latitude: number;
  longitude: number;
  description: string | undefined;
  startDate: string | undefined;
  endDate: string | undefined;
  category: string | undefined;
  statusId: number;
  participants: UserDto[] | undefined;
  gallery: GalleryDto;
}
