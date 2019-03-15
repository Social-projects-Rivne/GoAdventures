export interface UserDto {
    fullName: string;
    userName: string;
    email: string;
    avatarUrl?: string;
    password?: string;
    location?: string;

    phone?: string;
    role?: string;
    statusId?: string;
    id?: string;

    show?: boolean;
}
