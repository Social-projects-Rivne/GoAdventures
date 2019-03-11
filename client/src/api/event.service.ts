import axios, { AxiosResponse } from "axios";
import { EventDto } from '../interfaces/Event.dto';
import { serverUrl } from './url.config';



export const eventList = async (): Promise<AxiosResponse> => await axios.get('http://localhost:8080/event/all/', {
    headers: {
    'Authorization': `Bearer ${localStorage.getItem('tkn879')}`,
    'Content-Type': 'application/json'
    }
})