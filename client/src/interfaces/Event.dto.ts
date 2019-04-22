import { GalleryDto } from './Gallery.dto';
import { CategoryDto } from './Category.dto';
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
  category: CategoryDto;
  statusId: number;
  participants: UserDto[] | undefined;
  gallery: GalleryDto;
}
