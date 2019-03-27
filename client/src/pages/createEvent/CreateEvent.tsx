import React, { Component, createRef } from 'react';
import { createEventReq } from '../../api/requestCreateEvent';
import { EventSchema } from '../../validationSchemas/eventValidation';
import './CreateEvent.scss';
import './Leaflet.scss';
import { Map as LeafletMap, TileLayer, Marker, Popup } from 'react-leaflet';
import L from 'leaflet';
import LCG from 'leaflet-control-geocoder';
import { DropDown } from '../../components';
import DatePicker from "react-datepicker";

var category = 'Skateboarding';
var leafletMap = createRef<LeafletMap>();
var Rows = 4;

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
        const map = leafletMap.current;
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
        createEventReq({ ...this.state }, category);
        console.debug(this.state);
    }

    public render() {
        return (

            

                <div className='container-fluid'>

                    <div className="row">
                        <div className='col-xl-6 col-lg-6 col-md-6 col-sm-12'>
                            

                                <div className="form-group row">
                                    <label className='col-sm-2 col-form-label' htmlFor="Topic">Name of event</label>
                                    <div className="col-sm-10">
                                        <input type="text" className="form-control" id="Topic"
                                            placeholder="enter name of event"
                                            onChange={this.handleTopicChange} value={this.state.topic} />
                                    </div>
                                </div>


                                <div className="form-group-row">
                                    <label className='col-sm-2 col-form-label' htmlFor="Description">Description</label>
                                    <div className="col-sm-10">
                                        <textarea className="form-control" id="Description" placeholder="Enter description" rows={Rows} onChange={this.handleDescriptionChange}></textarea>
                                    </div>
                                </div>

                                <div className="form-group row">
                                <label className='col-sm-2 col-form-label' htmlFor="Location">
                                    Location
                                    </label>
                                    <div className="col-sm-10">
                        <input type="text" className="form-control" id="Location"
                                        aria-describedby="LocationHelp" placeholder="Choose location on map"
                                        onChange={this.handleLocationChange} value={this.state.location} readOnly />
                                </div>
                                </div>

                                <label className='list-group-item' htmlFor="Location">
                                    Choose category for the event
                    <DropDown onCategoryChange={this.handleCategory} />
                                </label>
                                <label className='list-group-item' htmlFor="Location">
                                    Choose start date
                                    <p></p>
                                    <DatePicker
                                        selected={this.state.startDate}
                                        onChange={this.handleStartDate}
                                        showTimeSelect
                                        timeFormat="HH:mm"
                                        timeIntervals={15}
                                        timeCaption="time"
                                        dateFormat="MMMM d, yyyy h:mm aa"
                                    />
                                </label>
                                <label className='list-group-item' htmlFor="Location">
                                    Choose end date
                                    <p></p>
                                    <DatePicker
                                        selected={this.state.endDate}
                                        onChange={this.handleEndDate}
                                        showTimeSelect
                                        timeFormat="HH:mm"
                                        timeIntervals={15}
                                        timeCaption="time"
                                        dateFormat="MMMM d, yyyy h:mm aa"
                                    />
                                </label>
                                <button type="button" className="btn btn-primary" onClick={this.handleSubmit} > Save </button>
                            
                        </div>
                    </div>
                    <div className='row' id='map'>
                        <div className='col-xl-6 col-lg-6 col-md-6 col-sm-12 d-sm-flex col d-none align-self-center'>
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
                                    url='http://{s}.tile.osm.org/{z}/{x}/{y}.png'
                                />
                                {this.state.currentPos && <Marker position={this.state.currentPos} draggable={true}>
                                    <Popup position={this.state.currentPos}>
                                        Current location: <pre>{JSON.stringify(this.state.currentPos, null, 2)}</pre>
                                    </Popup>
                                </Marker>}
                            </LeafletMap>
                            <div className='Home-heading d-flex flex-column align-items-baseline'>
                            </div>

                        </div>
                    </div>
                </div>
            
        );
    }
}
