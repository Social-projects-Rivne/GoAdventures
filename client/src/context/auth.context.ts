import React from 'react';
export const user = {
        authorized: false
    };

export const AuthContext = React.createContext(
        {
        authorize: (reqType: () => any): void => {},
        ...user,
    }
    );

