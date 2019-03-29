import React from 'react';

export const ProfileContext = React.createContext({
    choose: 'edit-profile',
    togleEditProfile: () => { },
    togleMyEvents: () => { },
    toogleAccountOverView: () => { }
})
