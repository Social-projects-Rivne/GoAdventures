import { AxiosResponse } from 'axios';
import React, { Component, SyntheticEvent } from 'react';
import { Field, FieldProps, Form, Formik, FormikProps } from 'formik';
import { TileLayer, Map, Marker, Popup } from 'react-leaflet';
import { MdDone } from 'react-icons/md';
import moment from 'moment';
import { deleteEvent, isOwner } from '../../../api/event.service';
import { Comments, Gallery, SettingsPanel } from '../../../components';
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

  public render() {
    return (
      <div className='container EventDetail'>
        <div className='row'>
          <div className='col-12 col-sm-12 col-md-6 col-lg-7 col-xl-7'>
            <Gallery {...this.state.eventProps.event.gallery} />
          </div>
          <div className='col-12 col-sm-12 col-md-6 col-lg-5 col-xl-5'>
            <div className='jumboton jumbotron-fluid'>
              <SettingsPanel>
                {{
                  left: (
                    <div>
                      <div className='d-flex flex-row align-content-center mt-3'>
                        <img
                          className='rounded-avatar-sm'
                          src='https://www.kidzone.ws/animal-facts/whales/images/beluga-whale-3.jpg'
                        />
                        <h2>{this.state.eventProps.event.topic}</h2>
                      </div>
                      <p>
                        Start:
                        {this.convertTime(
                          this.state.eventProps.event.startDate.toString()
                        )}
                      </p>
                      <p>
                        Ends:
                        {this.convertTime(
                          this.state.eventProps.event.endDate.toString()
                        )}
                      </p>
                    </div>
                  ),
                  right: (
                    <button
                      type='button'
                      className='btn btn-outline-info btn-sm'
                    >
                      Subscribe
                    </button>
                  )
                }}
              </SettingsPanel>
              <hr className='my-4' />
              <span className='lead'>
                {this.state.eventProps.event.description}
              </span>
              <hr className='my-4' />
              <div className='map'>
                <h2>Location and Destination points</h2>
                <div className='rounded'>
                  <Map
                  attributionControl={true}
                  zoomControl={false}
                  doubleClickZoom={true}
                  scrollWheelZoom={true}
                  dragging={true}
                  animate={true}
                  easeLinearity={0.35}
                    className='rounded'
                    center={[
                      this.state.eventProps.event.latitude,
                      this.state.eventProps.event.longitude
                    ]}
                    zoom={13}
                  >
                    <TileLayer url='https://cartodb-basemaps-{s}.global.ssl.fastly.net/dark_all/{z}/{x}/{y}.png' />
                    <Marker
                      position={[
                        this.state.eventProps.event.latitude,
                        this.state.eventProps.event.longitude
                      ]}
                    >
                      <Popup>
                        A pretty CSS3 popup. <br /> Easily customizable.
                      </Popup>
                    </Marker>
                  </Map>
                </div>
                {this.state.eventProps.event.location}
              </div>
              {this.state.isOwner ? (
                <div>
                  <button
                    onClick={this.handleDelete}
                    type='button'
                    className='btn btn-danger'
                  >
                    Delete
                  </button>
                  <button
                    onClick={() => {
                      this.state.eventProps.setEdit(true);
                    }}
                    type='button'
                    className='btn btn-success'
                  >
                    Edit
                  </button>
                </div>
              ) : (
                <div />
              )}

              <hr className='my-4' />
              <div>
                <h2>Comments</h2>
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
