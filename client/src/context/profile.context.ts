import React from 'react';

export const ProfileContext = React.createContext({
    choose: 'edit-profile',
    userProfile: {},
    setAvatar:(url: string) => {},
    togleEditProfile: () => {},
    togleMyEvents: () => {},
    toogleAccountOverView: () => {}
})

