import React, { Component, createRef } from 'react';
import { createEventReq } from '../../api/requestCreateEvent';
import './CreateEvent.scss';
import './Leaflet.scss';
import { Map as LeafletMap, TileLayer, Marker, Popup } from 'react-leaflet';
import L from 'leaflet';
import LCG from 'leaflet-control-geocoder';
import { DropDown } from '../../components';
import DatePicker from "react-datepicker";
import { RefObject } from 'react';
import { Redirect } from 'react-router';

interface ExtendetRef extends RefObject<LeafletMap> {
    leafletElement: any;
}

let category = 'Skateboarding';
let leafletMap = createRef<LeafletMap>() as ExtendetRef;
let Rows = 3;

export class CreateEvent extends Component<any, any> {

    constructor(props: any) {
        super(props);
        console.debug(props);
        this.state = {
            topic: '',
            startDate: new Date(),
            endDate: new Date(),
            location: '',
            latitude: 0,
            longitude: 0,
            description: '',
            currentPos: null,
            redirect: false
        };
        this.handleTopicChange = this.handleTopicChange.bind(this);
        this.handleDescriptionChange = this.handleDescriptionChange.bind(this);
        this.handleLocationChange = this.handleLocationChange.bind(this);
        this.handleCategory = this.handleCategory.bind(this);
        this.handleStartDate = this.handleStartDate.bind(this);
        this.handleEndDate = this.handleEndDate.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleCoord = this.handleCoord.bind(this);
    }

    handleTopicChange(event: any) {
        this.setState({ topic: event.target.value });
    }

    handleDescriptionChange(event: any) {
        this.setState({ description: event.target.value });
    }

    handleLocationChange(event: any) {
        this.setState({ location: event.target.value });
    }

    handleCategory(Category: any) {
        console.log("Category " + Category);
        category = Category;
    }

    handleStartDate(StartDate: any) {
        console.log("DIALOG " + StartDate);
        this.setState({ startDate: StartDate });
        console.log('startDate ', this.state.startDate);
        console.log('startDateToLocaleString ', this.state.startDate.toLocaleString());
    }

    handleEndDate(EndDate: any) {
        console.log("DIALOG " + EndDate);
        this.setState({ endDate: EndDate });
    }

    handleCoord(e: any) {
        const map = leafletMap.leafletElement;
        const geocoder = LCG.L.Control.Geocoder.nominatim();
        if (map != null) {
            geocoder.reverse(e.latlng, (map as any).options.crs.scale((map as any).getZoom()), (results: any) => {
                var r = results[0];
                if (r) {
                    console.log('r ', r);
                    this.setState({ location: r.name, latitude: r.center.lat, longitude: r.center.lng });
                    console.log('location: ', this.state.location);
                }
            });
        }
        this.setState({ currentPos: e.latlng });
    }

    handleSubmit(event: any) {
        if (this.state.startDate < this.state.endDate) {
            createEventReq({ ...this.state }, category);
            console.debug(this.state);
            this.setState({ redirect: true })
        }
        else {
            console.log('startDate > endDate ');
            alert("End date must be greater than the start date!");
        }
    }

    public render() {
        const isDisabled = this.state.topic.length > 0 && this.state.description.length > 0;
        console.log('Isdisabled ', isDisabled);
        var inputClass = 'invalid';
        if (!isDisabled)
            inputClass = 'valid';

        return (

            <div className='container'>

                <h1 className="text-center">New Event</h1>
                <div className="form-group row">
                    <label className='col-sm-4 col-form-label text-right' htmlFor="Topic">Name of event(required)</label>
                    <div className="col-sm-8">
                        <input type="text" className="form-control" id="Topic"
                            placeholder="enter name of event"
                            onChange={this.handleTopicChange} value={this.state.topic} />
                    </div>
                </div>


                <div className="form-group row">
                    <label className='col-sm-4 col-form-label text-right' htmlFor="Description">Description(required)</label>
                    <div className="col-sm-8">
                        <textarea className="form-control" id="Description" placeholder="Enter description" rows={Rows} onChange={this.handleDescriptionChange}></textarea>
                    </div>
                </div>

                <div className="form-group row">
                    <label className='col-sm-4 col-form-label text-right' htmlFor="Location">Location</label>
                    <div className="col-sm-8">
                        <input type="text" className="form-control" id="Location"
                            aria-describedby="LocationHelp" placeholder="Choose location on map"
                            onChange={this.handleLocationChange} value={this.state.location} readOnly />
                    </div>
                </div>

                <div className="form-group row">
                    <label className='col-sm-4 col-form-label text-right' htmlFor="Category">Choose category for the event</label>
                    <div className="col-sm-8">
                        <DropDown id="Category" onCategoryChange={this.handleCategory} />
                    </div>

                </div>
                <div className="form-group row">
                    <label className='col-sm-4 col-form-label text-right' htmlFor="StartDate">Start date</label>
                    <div className="col-sm-3">
                        <DatePicker
                            id="StartDate"
                            selected={this.state.startDate}
                            onChange={this.handleStartDate}
                            showTimeSelect
                            timeFormat="HH:mm"
                            timeIntervals={15}
                            timeCaption="time"
                            withPortal
                            dateFormat="MMMM d, yyyy h:mm aa"
                        />
                    </div>


                    <label className='col-sm-2 col-form-label text-right' htmlFor="EndDate">End date</label>
                    <div className="col-sm-3">
                        <DatePicker
                            id="EndDate"
                            selected={this.state.endDate}
                            onChange={this.handleEndDate}
                            showTimeSelect
                            timeFormat="HH:mm"
                            timeIntervals={15}
                            timeCaption="time"
                            withPortal
                            dateFormat="MMMM d, yyyy h:mm aa"

                        />
                    </div>

                </div>

                <div className='row' >
                    <div className='col' id="map" >
                        <LeafletMap
                            center={[50.37, 26.13]}
                            zoom={6}
                            attributionControl={true}
                            zoomControl={true}
                            doubleClickZoom={true}
                            scrollWheelZoom={true}
                            dragging={true}
                            animate={true}
                            easeLinearity={0.35}
                            onClick={this.handleCoord}
                            ref={(el: any) => leafletMap = el}
                        >
                            <TileLayer
                                url='https://cartodb-basemaps-{s}.global.ssl.fastly.net/dark_all/{z}/{x}/{y}.png'
                            />
                            {this.state.currentPos && <Marker position={this.state.currentPos} draggable={true}>
                                <Popup position={this.state.currentPos}>
                                    Current location: <pre>{JSON.stringify(this.state.currentPos, null, 2)}</pre>
                                </Popup>
                            </Marker>}
                        </LeafletMap>

                    </div>
                </div>
                <div className="row justify-content-center btns-content">
                    <button type="button" className="btn btn-primary col-lg-2 col-sm-12" onClick={this.handleSubmit} disabled={!isDisabled}> Save </button>
                    {this.state.redirect ? (
                            <Redirect
                                to={{
                                    pathname: `/profile`
                                }}
                            />
                        ) : null}
                </div>
            </div>

        );
    }
}