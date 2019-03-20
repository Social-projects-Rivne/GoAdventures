import React, { Component } from 'react';
import { CSSProperties } from 'react';
import { createEventReq } from '../../api/requestCreateEvent';
import { DropDown } from '../../components/';
import { Dialog } from '../../components/';
import { InputSettings } from '../../components/dialog-window/interfaces/input.interface';
import { EventSchema } from '../../validationSchemas/eventValidation';
import '../home/Home.scss';

export class CreateEvent extends Component<any, any> {
    private createEventDialogStyles: CSSProperties = {
        height: '39rem',
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
            placeholder: '2019-01-01',
            type: 'text'
        },
        {
            field_name: 'endDate',
            label_value: 'End Date of event',
            placeholder: '2019-12-31',
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
            label_value: 'Description',
            placeholder: 'Description',
            type: 'text'
        }
    ];

    constructor(props: any) {
        super(props);
        console.debug(props);
    }

    public submitCreateEventRequest(data: object, categ?:string): Promise<string> {
        return createEventReq(data, categ);
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
                                    validationSchema={EventSchema}
                                    handleSubmit={this.submitCreateEventRequest}
                                    inputs={this.createEventInputSettings}
                                    button_text='Create event'
                                    header='Create event'
                                    inline_styles={this.createEventDialogStyles}
                                    redirect={{ routerProps: this.props.routerProps, redirectURL: '/profile' }}
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
