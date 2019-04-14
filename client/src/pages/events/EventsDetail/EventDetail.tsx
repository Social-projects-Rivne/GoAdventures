import { AxiosResponse } from 'axios';
import React, { Component } from 'react';
import { Field, FieldProps, Form, Formik, FormikProps } from 'formik';
import { TileLayer, Map, Marker, Popup } from 'react-leaflet';
import { MdDone, MdLockOpen, MdEdit, MdDelete, MdLock } from 'react-icons/md';
import moment from 'moment';
import {
  deleteEvent,
  isOwner,
  closeEvent,
  openEvent
} from '../../../api/event.service';
import { Comments, Gallery } from '../../../components';
import { commentsSchema } from '../../../validationSchemas/commentValidation';
import './EventDetail.scss';

interface FormValue {
  comment: string;
}

export class EventDetail extends Component<any, any> {
  public static getDerivedStateFromProps(nextProps: any, prevState: any): any {
    if (Object.is(nextProps.event, prevState.eventProps.event) === false) {
      return { ...prevState, eventProps: { ...nextProps } };
    } else {
      return false;
    }
  }
  constructor(props: any) {
    super(props);
    this.state = {
      eventProps: { ...this.props },
      isOwner: false
    };
    this.handleDelete = this.handleDelete.bind(this);
    this.handleClose = this.handleClose.bind(this);
    this.handleOpen = this.handleOpen.bind(this);
  }

  public convertTime(date: string) {
    const dateFormat = 'dddd, DD MMMM YYYY';
    return moment(date)
      .local()
      .format(dateFormat)
      .toString();
  }

  public componentDidMount() {
    isOwner(this.state.eventProps.event.id).then(
      (res: AxiosResponse): any => {
        if (res.status >= 200 && res.status <= 300) {
          this.setState({
            isOwner: true
          });
        } else {
          this.setState({
            isOwner: false
          });
        }
      }
    );
  }

  public handleDelete() {
    deleteEvent(this.state.eventProps.event.id).then(
      (res: AxiosResponse): any => {
        if (res.status >= 200 && res.status <= 300) {
          this.props.routerProps.history.push('/profile');
        } else {
        }
      }
    );
  }

  public handleClose() {
    console.log('status ', this.state.eventProps.event.statusId);
    closeEvent(this.state.eventProps.event.id).then(
      (res: AxiosResponse): any => {
        if (res.status >= 200 && res.status <= 300) {
          this.props.routerProps.history.push('/profile');
        } else {
        }
      }
    );
  }

  public handleOpen() {
    openEvent(this.state.eventProps.event.id).then(
      (res: AxiosResponse): any => {
        if (res.status >= 200 && res.status <= 300) {
          this.props.routerProps.history.push('/profile');
        } else {
        }
      }
    );
  }

  public render() {
    const style =
      this.state.eventProps.event.statusId === 2 ? { display: 'none' } : {};

    return (
      <div className='container page-container EventDetail'>
        <div className='row'>
          <div className='col-12 col-sm-12 col-md-6 col-lg-7 col-xl-7'>
            <Gallery class='gallery' {...this.state.eventProps.event.gallery} />
          </div>
          <div className='col-12 col-sm-12 col-md-6 col-lg-5 col-xl-5'>
            <div className='jumboton jumbotron-fluid'>
              <div className='row mt-2 mb-2'>
                <div className='col-6 '>
                  <div className='row'>
                    <h3 className='header'>
                      {this.state.eventProps.event.topic}{' '}
                    </h3>
                    {this.state.eventProps.event.statusId === 2 ? (
                      <p style={{ color: 'red' }}>CLOSED</p>
                    ) : null}
                  </div>
                </div>
                <div className='col-6'>
                  <div className='d-flex  justify-content-end'>
                    {!this.state.isOwner ? (
                      <button
                        type='button'
                        className='btn btn-outline-info btn-sm'
                        style={style}
                      >
                        Subscribe
                      </button>
                    ) : (
                      <div>
                        <button
                          onClick={this.handleDelete}
                          type='button'
                          className='btn btn-lg btn-outline-danger ml-1'
                        >
                          <MdDelete />
                        </button>
                        <button
                          onClick={() => {
                            this.state.eventProps.setEdit(true);
                          }}
                          type='button'
                          className='btn btn-lg btn-outline-success ml-1'
                        >
                          <MdEdit />
                        </button>
                        {this.state.eventProps.event.statusId === 2 ? (
                          <button
                            onClick={this.handleOpen}
                            type='button'
                            className='btn btn-lg btn-outline-success ml-1'
                          >
                            <MdLockOpen />
                          </button>
                        ) : (
                          <button
                            onClick={this.handleClose}
                            type='button'
                            className='btn btn-lg btn-outline-warning ml-1'
                          >
                            <MdLock />
                          </button>
                        )}
                      </div>
                    )}
                  </div>
                </div>
              </div>

              <div className='content-column d-flex flex-column h-100'>
                <p>
                  Start:
                  {this.convertTime(
                    this.state.eventProps.event.startDate.toString()
                  )}
                </p>
                <p>
                  Ends:
                  {this.state.eventProps.event.endDate === '0'
                    ? ''
                    : this.convertTime(
                        this.state.eventProps.event.endDate.toString()
                      )}
                </p>
              </div>

              <hr className='my-3' />
              <span className='lead'>
                {this.state.eventProps.event.description}
              </span>
              <hr className='my-3' />
              <div className='map'>
                <h3>Location and Destination points</h3>
                <div className='rounded'>
                  <Map
                    attributionControl={true}
                    zoomControl={false}
                    doubleClickZoom={true}
                    scrollWheelZoom={true}
                    dragging={true}
                    animate={true}
                    easeLinearity={0.35}
                    className='rounded map-layer'
                    center={[
                      this.state.eventProps.event.latitude,
                      this.state.eventProps.event.longitude
                    ]}
                    zoom={13}
                  >
                    <TileLayer
                      attribution='&amp;copy <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
                      url='https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png'
                    />
                    <Marker
                      position={[
                        this.state.eventProps.event.latitude,
                        this.state.eventProps.event.longitude
                      ]}
                    >
                      <Popup>{this.state.eventProps.event.location}</Popup>
                    </Marker>
                  </Map>
                </div>
                {this.state.eventProps.event.location}
              </div>
              <hr className='my-4' />
              <div>
                <h3>Comments</h3>
                <div>
                  <Formik
                    validationSchema={commentsSchema}
                    initialValues={{ comment: '' }}
                    enableReinitialize={true}
                    validateOnBlur={true}
                    validateOnChange={true}
                    onSubmit={() => {}}
                    render={(props: FormikProps<FormValue>) => (
                      <Form>
                        <Field
                          name='comment'
                          render={({ field, form }: FieldProps<FormValue>) => {
                            return (
                              <div>
                                <input
                                  className='form-control'
                                  type='text'
                                  {...field}
                                  placeholder='Comment...'
                                  name='comment'
                                />
                                {form.touched.comment &&
                                form.errors.comment &&
                                form.errors.comment ? (
                                  <div className='invalid-feedback'>
                                    {form.errors.comment}
                                  </div>
                                ) : (
                                  <div className='valid-feedback'>
                                    <MdDone /> Press enter to add comment
                                  </div>
                                )}
                              </div>
                            );
                          }}
                        />
                      </Form>
                    )}
                  />
                </div>
                <hr className='my-4' />
                <div>
                  <Comments
                    {...{
                      avatar:
                        'https://www.kidzone.ws/animal-facts/whales/images/beluga-whale-3.jpg',
                      participant: 'Jeremy Mafioznik',
                      text: 'Dolore ipsum',
                      hashtags: ['pussy', 'money', 'weed']
                    }}
                  />
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}
