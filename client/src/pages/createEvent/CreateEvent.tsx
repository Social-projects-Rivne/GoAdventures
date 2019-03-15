import React, { Component } from 'react';
import { CSSProperties } from 'react';
import { Redirect } from 'react-router';
import { createEventReq } from '../../api/requestCreateEvent';
import {Dialog} from '../../components/';
import { InputSettings } from '../../components/dialog-window/interfaces/input.interface';
import { AuthContext } from '../../context/auth.context';
import '../home/Home.scss';
import {SignupSchema} from "../../validationSchemas/authValidation";

export class CreateEvent extends Component {
    private createEventDialogStyles: CSSProperties = {
        height: '35rem',
        maxWidth: '20rem',
        opacity: 0.9
    };
    private createEventInputSettings: InputSettings[] = [
        {
            field_name: 'topic',
            label_value: 'Topic of event',
            placeholder: 'Topic',
            type: 'text'
        },
        {
            field_name: 'startDate',
            label_value: 'Start date of event',
            placeholder: '01.01.2019',
            type: 'text'
        },
        {
            field_name: 'endDate',
            label_value: 'End Date of event',
            placeholder: '02.01.2019',
            type: 'text'
        },
        {
            field_name: 'location',
            label_value: 'Location',
            placeholder: 'Location',
            type: 'text'
        },
        {
            field_name: 'description',
            label_value: 'description',
            placeholder: 'description',
            type: 'text'
        }
    ];

    public submitCreateEventRequest(data: object): Promise<string>{
        return createEventReq(data);
    }

    public render() {
        return (
            <div className='Home-content'>
                <div className='container'>
                    <div className='row'>
                    </div>
                    <div className='row'>
                        <div className='col-xl-6 col-lg-6 col-md-6 col-sm-12 d-sm-flex col d-none align-self-center'>
                            <div className='Home-heading d-flex flex-column align-items-baseline'>
                                <h2>GO</h2>
                                <h2>Adventures</h2>
                            </div>
                        </div>
                        <div className='col-xl-6 col-lg-6 col-md-6 col-sm-12'>
                            <div className='Home__signup'>

                                            <Dialog
                                                handleSubmit={this.submitCreateEventRequest}
                                                inputs={this.createEventInputSettings}
                                                button_text='Create event'
                                                header='Create event'
                                                inline_styles={this.createEventDialogStyles}
                                                event={true}
                                            />


                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}
