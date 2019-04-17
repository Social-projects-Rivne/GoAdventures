import React from 'react';

export const ProfileContext = React.createContext({
    choose: 'edit-profile',
    context: {
        avatarUrl: '',
    },
    togleEditProfile: () => { },
    togleMyEvents: () => { },
    toogleAccountOverView: () => { }
})

