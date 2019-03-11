import React, { Component } from 'react'

import { AxiosResponse } from 'axios';
import { EventDto } from '../interfaces/Event.dto';
import { getEventData } from '../api/event.service';
import {getUserData} from "../api/user.service";
import {UserDto} from "../interfaces/User.dto";


export class Event extends Component<any, EventDto>{
    constructor(props: any) {
        super(props)
        this.state = {
            description: '',
            topic: '',
            start_date: '',

        }

    }

    public componentDidMount() {                                  //сеттер на пропси зверху з api
        getEventData().then((response: AxiosResponse<EventDto>) =>
            this.setState({
                ...response.data
            })
        );
    }




    }





