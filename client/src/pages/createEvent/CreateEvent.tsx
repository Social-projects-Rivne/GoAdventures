import React, { Component, ChangeEvent } from 'react';
import { CSSProperties } from 'react';
import { createEventReq } from '../../api/requestCreateEvent';
import { Dialog } from '../../components/';
import { InputSettings } from '../../components/dialog-window/interfaces/input.interface';
import { EventSchema } from '../../validationSchemas/eventValidation';
import '../home/Home.scss';
import './CreateEvent.scss';
import { Map as LeafletMap, TileLayer, Marker, Popup } from 'react-leaflet';
import {DropDown, Datepicker} from '../../components';
import DatePicker from "react-datepicker";

var category = '';
var currentPos = [50.37, 26.13];

export class CreateEvent extends Component<any, any> {

    constructor(props: any) {
        super(props);
        console.debug(props);
        this.state = {
            topic: '',
            startDate: new Date(),
            endDate: new Date(),
            location: '',
            description: '',
            currentPos:null
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

    public submitCreateEventRequest(data: object, categ?:string): Promise<string> {
        return createEventReq(data, categ);
    }

    handleTopicChange(event: any) {                
        console.log('topic ' + event.target.value);
        this.setState({ topic: event.target.value });
    }

    handleDescriptionChange(event: any) {                
        console.log('description ' + event.target.value);
        this.setState({ description: event.target.value });
    }

    handleLocationChange(event: any) {                
        console.log('location ' + event.target.value);
        this.setState({ location: event.target.value });
    }

    handleCategory(fromChild:any) {
        console.log("DIALOG " + fromChild);
        category = fromChild;
    }

    handleStartDate(fromChild:any) {
      console.log("DIALOG " + fromChild);
      this.setState({ startDate: fromChild });
  }

  handleEndDate(fromChild:any) {
    console.log("DIALOG " + fromChild);
    this.setState({ endDate: fromChild });
}

handleCoord(e:any){
    currentPos = e.latlng;
    this.setState({currentPos: e.latlng});
}

    handleSubmit(event: any) {      
        createEventReq({...this.state}, category);
        console.log(this.state.currentPos);
    }

    public render() {
        return (

            <div className='Home-content'>

                <div className='container'>

                    <div className='row'>

                    </div>
                    <div className='row'>
                        <div className='col-xl-6 col-lg-6 col-md-6 col-sm-12 d-sm-flex col d-none align-self-center'>
                        <LeafletMap
                                                    center={[50.37, 26.13]}
                                                    zoom={6}
                                                    maxZoom={10}
                                                    attributionControl={true}
                                                    zoomControl={true}
                                                    doubleClickZoom={true}
                                                    scrollWheelZoom={true}
                                                    dragging={true}
                                                    animate={true}
                                                    easeLinearity={0.35}
                                                    onClick={this.handleCoord}
                                                  >
                                                    <TileLayer
              url='http://{s}.tile.osm.org/{z}/{x}/{y}.png'
          />
          { this.state.currentPos && <Marker position={this.state.currentPos} draggable={true}>
            <Popup position={this.state.currentPos}>
              Current location: <pre>{JSON.stringify(this.state.currentPos, null, 2)}</pre>
            </Popup>
          </Marker>}
                                                  </LeafletMap>
                            <div className='Home-heading d-flex flex-column align-items-baseline'>
                            </div>

                        </div>
                        <div className='col-xl-6 col-lg-6 col-md-6 col-sm-12'>
                            <div className='Home__signup'>
                            <label className='list-group-item' htmlFor="Topic">
                          Topic
                        <input type="text" className="form-control" id="Topic"
                            aria-describedby="topicHelp" placeholder="enter topic"
                            onChange={this.handleTopicChange} value={this.state.topic} />
                    </label>
                    <label className='list-group-item' htmlFor="Description">
                          Description
                        <input type="text" className="form-control" id="Description"
                            aria-describedby="DecriptionHelp" placeholder="enter description"
                            onChange={this.handleDescriptionChange} value={this.state.description} />
                    </label>
                    <label className='list-group-item' htmlFor="Location">
                          Location
                        <input type="text" className="form-control" id="Location"
                            aria-describedby="LocationHelp" placeholder="enter location"
                            onChange={this.handleLocationChange} value={this.state.locatiion} />
                    </label>
                    <label className='list-group-item' htmlFor="Location"> 
                    Choose category for the event
                    <DropDown onCategoryChange={this.handleCategory}/>
                    </label>
                    <label className='list-group-item' htmlFor="Location"> 
                    Choose start date
                    <DatePicker
                     selected={this.state.startDate}
                     onChange={this.handleStartDate}
                     />
                  </label>
                  <label className='list-group-item' htmlFor="Location"> 
                  Choose end date
                  <DatePicker
                     selected={this.state.endDate}
                     onChange={this.handleEndDate}
                     />
                  </label>
                    <button type="button" className="btn btn-primary" onClick={this.handleSubmit} > Save </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}