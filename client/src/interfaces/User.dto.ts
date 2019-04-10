export interface UserDto {
  fullname: string;
  username: string;
  email: string;
  avatarUrl?: string;
  password?: string;
  location?: string;
  newPassword?: string;
  repeatNewPassword?: string;

  phone?: string;
  role?: string;
  //statusId?: string;
  //id?: string;

  //show?: boolean;
  //errorMesage?: boolean;
}
