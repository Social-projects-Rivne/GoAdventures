import axios, { AxiosResponse } from "axios";



export const getUserData = async (): Promise<AxiosResponse> => await axios.get('http://localhost:8080/profile/page', {
    headers: {
        'Authorization': `Bearer ${localStorage.getItem('tkn879')}`,
        'Content-Type': 'application/json'
    }
})





