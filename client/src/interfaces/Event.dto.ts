import { GalleryDto } from './Gallery.dto';
import { UserDto } from './User.dto';

export interface EventDto {
  id: number | undefined;
  topic: string | undefined;
  location: string | undefined;
  description: string | undefined;
  startDate: string | undefined;
  endDate: string | undefined;
  participants: UserDto[] | undefined;
  gallery: GalleryDto | undefined;
}
