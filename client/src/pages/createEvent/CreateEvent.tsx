import React, { Component, createRef, RefObject } from 'react';
import { createEventReq } from '../../api/requestCreateEvent';
import './CreateEvent.scss';
import './Leaflet.scss';
import { Map as LeafletMap, TileLayer, Marker, Popup } from 'react-leaflet';
import LCG from 'leaflet-control-geocoder';
import { DropDown } from '../../components';
import DatePicker from 'react-datepicker';
import { Redirect } from 'react-router';
import { GalleryDto } from '../../interfaces/Gallery.dto';
import { UploadInput } from '../../components/upload-input/UploadInput';
import { ErrorMessage } from '../../interfaces/ErrorMessage';

interface ExtendetRef extends RefObject<LeafletMap> {
  leafletElement: any;
}

let category = 'Skateboarding';
let leafletMap = createRef<LeafletMap>() as ExtendetRef;
const Rows = 3;

export class CreateEvent extends Component<any, any> {
  constructor(props: any) {
    super(props);
    console.debug(props);
    this.state = {
      newEvent: {
        topic: '',
        startDate: new Date(),
        endDate: new Date(),
        location: '',
        latitude: 0,
        longitude: 0,
        description: '',
        gallery: {} as GalleryDto
      },
      redirect: false,
      currentPos: null,
      errorMessages: {} as ErrorMessage
    };

    this.handleErrors = this.handleErrors.bind(this);
    this.handleAddGallery = this.handleAddGallery.bind(this);
    this.handleTopicChange = this.handleTopicChange.bind(this);
    this.handleDescriptionChange = this.handleDescriptionChange.bind(this);
    this.handleLocationChange = this.handleLocationChange.bind(this);
    this.handleCategory = this.handleCategory.bind(this);
    this.handleStartDate = this.handleStartDate.bind(this);
    this.handleEndDate = this.handleEndDate.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleCoord = this.handleCoord.bind(this);
  }

  public handleErrors(value: ErrorMessage) {
    this.setState({ errorMessages: { ...value } });
    console.debug(this.state);
  }

  public handleAddGallery(value: GalleryDto) {
    this.setState({ gallery: { ...value } });
    console.debug(this.state);
  }

  public handleTopicChange(event: any) {
    this.setState({ topic: event.target.value });
  }

  public handleDescriptionChange(event: any) {
    this.setState({ description: event.target.value });
  }

  public handleLocationChange(event: any) {
    this.setState({ location: event.target.value });
  }

  public handleCategory(Category: any) {
    console.log('Category ' + Category);
    category = Category;
  }

  public handleStartDate(StartDate: any) {
    console.log('DIALOG ' + StartDate);
    this.setState({ startDate: StartDate });
    console.log('startDate ', this.state.newEvent.startDate);
    console.log(
      'startDateToLocaleString ',
      this.state.newEvent.startDate.toLocaleString()
    );
  }

  public handleEndDate(EndDate: any) {
    console.log('DIALOG ' + EndDate);
    this.setState({ endDate: EndDate });
  }

  public handleCoord(e: any) {
    const map = leafletMap.leafletElement;
    const geocoder = LCG.L.Control.Geocoder.nominatim();
    if (map !== null) {
      geocoder.reverse(
        e.latlng,
        (map as any).options.crs.scale((map as any).getZoom()),
        (results: any) => {
          const r = results[0];
          if (r) {
            console.log('r ', r);
            this.setState({
              location: r.name,
              latitude: r.center.lat,
              longitude: r.center.lng
            });
            console.log('location: ', this.state.newEvent.location);
          }
        }
      );
    }
    this.setState({ currentPos: e.latlng });
  }

  public handleSubmit(event: any) {
    if (this.state.newEvent.startDate < this.state.newEvent.endDate) {
      createEventReq({ ...this.state.newEvent }, category);
      console.debug(this.state.newEvent);
      this.setState({ redirect: true });
    } else {
      console.log('startDate > endDate ');
      alert('End date must be greater than the start date!');
    }
  }

  public componentDidMount() {
    setTimeout(() => {
      if (leafletMap) {
        leafletMap.leafletElement.invalidateSize();
      }
    }, 50);
  }

  public render() {
    const isDisabled =
      this.state.newEvent.topic.length > 0 &&
      this.state.newEvent.description.length > 0;
    console.log('Isdisabled ', isDisabled);
    let inputClass = 'invalid';
    if (!isDisabled) {
      inputClass = 'valid';
    }

    return (
      <div className='container'>
        <h1 className='text-center'>New Event</h1>
        <div className='form-group row'>
          <label className='col-sm-4 col-form-label text-right' htmlFor='Topic'>
            Name of event(required)
          </label>
          <div className='col-sm-8'>
            <input
              type='text'
              className='form-control'
              id='Topic'
              placeholder='Enter event name'
              onChange={this.handleTopicChange}
              value={this.state.newEvent.topic}
            />
          </div>
        </div>

        <div className='form-group row'>
          <label
            className='col-sm-4 col-form-label text-right'
            htmlFor='Description'
          >
            Description(required)
          </label>
          <div className='col-sm-8'>
            <textarea
              className='form-control'
              id='Description'
              placeholder='Enter description'
              rows={Rows}
              onChange={this.handleDescriptionChange}
            />
          </div>
        </div>

        <div className='form-group row'>
          <label
            className='col-sm-4 col-form-label text-right'
            htmlFor='Location'
          >
            Location
          </label>
          <div className='col-sm-8'>
            <input
              type='text'
              className='form-control'
              id='Location'
              aria-describedby='LocationHelp'
              placeholder='Choose location on map'
              onChange={this.handleLocationChange}
              value={this.state.newEvent.location}
              readOnly
            />
          </div>
        </div>

        <div className='form-group row'>
          <label
            className='col-sm-4 col-form-label text-right'
            htmlFor='Category'
          >
            Choose category for the event
          </label>
          <div className='col-sm-8'>
            <DropDown id='Category' onCategoryChange={this.handleCategory} />
          </div>
        </div>
        <div className='form-group row'>
          <label
            className='col-sm-4 col-form-label text-right'
            htmlFor='StartDate'
          >
            Start date
          </label>
          <div className='col-sm-3'>
            <DatePicker
              id='StartDate'
              selected={this.state.newEvent.startDate}
              onChange={this.handleStartDate}
              showTimeSelect
              timeFormat='HH:mm'
              timeIntervals={15}
              timeCaption='time'
              withPortal
              dateFormat='MMMM d, yyyy h:mm aa'
            />
          </div>

          <label
            className='col-sm-2 col-form-label text-right'
            htmlFor='EndDate'
          >
            End date
          </label>
          <div className='col-sm-3'>
            <DatePicker
              id='EndDate'
              selected={this.state.newEvent.endDate}
              onChange={this.handleEndDate}
              showTimeSelect
              timeFormat='HH:mm'
              timeIntervals={15}
              timeCaption='time'
              withPortal
              dateFormat='MMMM d, yyyy h:mm aa'
            />
          </div>
        </div>
        <div className='row'>
          <div className='col'>
            <label htmlFor=''>Upload images for your event</label>
            <UploadInput
              {...{
                setErrors: this.handleErrors,
                setGallery: this.handleAddGallery
              }}
            />
          </div>
        </div>

        <div className='row'>
          <div className='col' id='map'>
            <LeafletMap
              center={[50.37, 26.13]}
              zoom={6}
              attributionControl={true}
              zoomControl={false}
              doubleClickZoom={true}
              scrollWheelZoom={true}
              dragging={true}
              animate={true}
              easeLinearity={0.35}
              onClick={this.handleCoord}
              ref={(el: any) => (leafletMap = el)}
            >
              <TileLayer
                attribution='&amp;copy <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
                url='https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png'
              />
              {this.state.currentPos && (
                <Marker position={this.state.currentPos} draggable={true}>
                  <Popup position={this.state.currentPos}>
                    Current location:{' '}
                    <pre>
                      {JSON.stringify(this.state.newEvent.currentPos, null, 2)}
                    </pre>
                  </Popup>
                </Marker>
              )}
            </LeafletMap>
          </div>
        </div>
        <div className='row justify-content-center btns-content'>
          <button
            type='button'
            className='btn btn-primary col-lg-2 col-sm-12'
            onClick={this.handleSubmit}
            disabled={!isDisabled}
          >
            {' '}
            Save{' '}
          </button>
          {this.state.redirect ? (
            <Redirect
              push
              to={{
                pathname: `/profile`,
                state: {
                  ...this.state.newEvent
                }
              }}
            />
          ) : null}
        </div>
      </div>
    );
  }
}
