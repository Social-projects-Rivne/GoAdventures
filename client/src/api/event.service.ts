import axios, { AxiosResponse } from "axios";
//написати метод(знайти івенти по токену)



export const getEventData = async (): Promise<AxiosResponse> => await axios.get('http://localhost:8080/profile/getevent', {
    headers: {
        'Authorization': `Bearer ${localStorage.getItem('tkn879')}`,
        'Content-Type': 'application/json'

    }


});

