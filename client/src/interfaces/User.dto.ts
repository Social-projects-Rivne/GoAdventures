import { BlockOverflowProperty } from 'csstype';

export interface UserDto {
    fullName: string;
    userName: string;
    email: string;
    avatarUrl?: string;
    password?: string;
    location?: string;
    newPassword?: string;
    repeatNewPassword?: string;


    phone?: string;
    role?: string;
    statusId?: string;
    id?: string;

    show?: boolean;
    errorMesage?: boolean;

}
