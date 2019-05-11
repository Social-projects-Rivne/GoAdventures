import { AxiosResponse } from 'axios';
import React, { Component } from 'react';
import { Field, FieldProps, Form, Formik, FormikProps } from 'formik';
import { TileLayer, Map, Marker, Popup } from 'react-leaflet';
import { MdDone, MdLockOpen, MdEdit, MdDelete, MdLock } from 'react-icons/md';
import moment from 'moment';
import DatePicker from 'react-datepicker';
// import TimePicker from 'rc-time-picker';

import {
  deleteEvent,
  isOwner,
  closeEvent,
  openEvent,
  isSubscribe,
  subscribe,
  unSubscribe,
  scheduleEmail,
  deleteScheduleEmail
} from '../../../api/event.service';
import { Feedback, Gallery } from '../../../components';
import { commentsSchema } from '../../../validationSchemas/commentValidation';
import './EventDetail.scss';
import { EventDto } from '../../../interfaces/Event.dto';
import { withRouter } from 'react-router-dom';
import { RouterProps } from 'react-router';
import { addFeedbackRequest } from '../../../api/feedback.service';

interface EventDetailState {
  routerProps: RouterProps;
  isOwner: boolean;
  eventProps: {
    event: EventDto;
    setEdit: any;
    setIsLoading: any;
  };
}

interface FormValue {
  comment: string;
}

const l = 's';
class EventDetail extends Component<any, any> {
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
      timeToAlert: new Date(2013, 2, 1, 1, 10),
      showAlert: false,
      isSchedule: true,
      errorMessage: {
        message: ''
      },
      eventProps: { ...this.props },
      isOwner: false,
      isSubs: true,
      newEventFeedback: {}
    };
    console.debug('state', this.state);
    this.handleDelete = this.handleDelete.bind(this);
    this.handleClick = this.handleClick.bind(this);
    this.handleClose = this.handleClose.bind(this);
    this.handleOpen = this.handleOpen.bind(this);
    this.timePickerChange = this.timePickerChange.bind(this);
    this.showAlertSwitcher = this.showAlertSwitcher.bind(this);
    this.clearErrorMessage = this.clearErrorMessage.bind(this);
  }

  public convertTime(date: string) {
    const dateFormat = 'dddd, DD MMMM YYYY HH:mm';
    return moment(date)
      .local()
      .format(dateFormat)
      .toString();
  }
  public convertTimeSchedule(date: string) {
    const dateFormat = 'HH:mm';
    return moment(date)
      .local()
      .format(dateFormat)
      .toString();
  }

  public componentDidMount() {
    isSubscribe(this.state.eventProps.event.id)
      .then(
        (res: AxiosResponse): any => {
          this.setState({
            isSubs: true
          });
        }
      )
      .catch((error: any) => {
        this.setState({
          isSubs: false
        });
      });

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
          this.props.history.push('/profile');
        } else {
        }
      }
    );
  }
  public async handleClick() {
    if (this.state.isSubs) {
      await unSubscribe(this.state.eventProps.event.id).then(
        (res: AxiosResponse): any => {
          if (res.status >= 200 && res.status <= 300) {
            this.setState({
              isSubs: false
            });
          }
        }
      );
      console.debug('delete schedule');
      deleteScheduleEmail(this.state.eventProps.event);
    } else {
      const date1 = this.state.timeToAlert;
      const timeToAlert = this.convertTimeSchedule(date1);
      console.debug('time to alert', timeToAlert);
      if (this.state.isSchedule) {
        await scheduleEmail(this.state.eventProps.event, timeToAlert).catch(
          (err) => {
            this.setState({ errorMessage: { ...err.response.data } }, () => {
              window.setTimeout(() => {
                this.setState({ errorMessage: { message: '' } });
              }, 3500);
            });
          }
        );
      }
      if (this.state.errorMessage.message === '') {
        await subscribe(this.state.eventProps.event.id).then(
          (res: AxiosResponse): any => {
            if (res.status >= 200 && res.status <= 300) {
              this.setState({
                isSubs: true
              });
            }
          }
        );
      }
    }
  }

  public handleClose() {
    closeEvent(this.state.eventProps.event.id).then(
      (res: AxiosResponse): any => {
        if (res.status >= 200 && res.status <= 300) {
          this.props.history.push('/profile');
        } else {
        }
      }
    );
  }

  public handleOpen() {
    openEvent(this.state.eventProps.event.id).then(
      (res: AxiosResponse): any => {
        if (res.status >= 200 && res.status <= 300) {
          this.props.history.push('/profile');
        } else {
        }
      }
    );
  }
  public timePickerChange(time: Date) {
    console.debug('TIME PICKER', time);
    this.setState({ timeToAlert: time });
  }
  public showAlertSwitcher() {
    this.setState({ showAlert: !this.state.showAlert });
    console.debug('show alert window', this.state.showAlert);
  }
  public schedulerSwitcher() {
    this.setState({ isSchedule: !this.state.isSchedule });
    console.debug('is schedule', this.state.isSchedule);
  }
  public clearErrorMessage() {
    this.setState({ errorMessage: { message: '' } });
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
                  <div className='d-flex justify-content-end'>
                    {!this.state.isOwner ? (
                      <div>
                        {this.state.isSubs ? (
                          <button
                            type='button'
                            className='btn btn-outline-danger btn-sm'
                            onClick={async () => {
                              if (this.state.showAlert) {
                                await this.showAlertSwitcher();
                              }
                              this.handleClick();
                            }}
                          >
                            Subscribed
                          </button>
                        ) : (
                          <div>
                            {this.state.eventProps.event.statusId ===
                            2 ? null : (
                              <button
                                type='button'
                                className='btn btn-info btn-sm'
                                onClick={async () => {
                                  if (!this.state.isSchedule) {
                                    await this.schedulerSwitcher();
                                  }
                                  this.showAlertSwitcher();
                                }}
                              >
                                Subscribe
                              </button>
                            )}

                            {this.state.showAlert === true ? (
                              <div className='TimepickerWrapper'>
                                <label className='notifyText'>
                                  Notify time(before event start)
                                </label>

                                <DatePicker
                                  className='TimePicker'
                                  selected={this.state.timeToAlert}
                                  showTimeSelect
                                  onChange={this.timePickerChange}
                                  showTimeSelectOnly
                                  timeIntervals={15}
                                  dateFormat='H:mm'
                                  timeFormat='HH:mm'
                                  timeCaption='hh  mm'
                                />
                                <br />

                                <button
                                  className='btn btn-info btn-sm'
                                  onClick={this.handleClick}
                                >
                                  Ok
                                </button>
                                <button
                                  onClick={async () => {
                                    await this.schedulerSwitcher();
                                    this.clearErrorMessage();
                                    this.handleClick();
                                  }}
                                  className='btn btn-info btn-sm'
                                >
                                  Without notification
                                </button>
                                <div className='Errors-messages timePicker'>
                                  {this.state.errorMessage.message !== '' ? (
                                    <div
                                      className='alert alert-danger alert-dismissible fade show errorTimePickerMessage'
                                      role='alert'
                                    >
                                      <strong>
                                        {this.state.errorMessage.message}
                                      </strong>
                                      <button
                                        type='button'
                                        onClick={this.clearErrorMessage}
                                        className='close'
                                        data-dismiss='alert'
                                        aria-label='Close'
                                        ref='qwe'
                                      >
                                        <span aria-hidden='true'>&times;</span>
                                      </button>
                                    </div>
                                  ) : null}
                                </div>
                              </div>
                            ) : null}
                          </div>
                        )}
                      </div>
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
                      url='https://tiles.wmflabs.org/hikebike/{z}/{x}/{y}.png'
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
                    onSubmit={async (values, actions): Promise<void> => {
                      this.setState({
                        newEventFeedback: await addFeedbackRequest({
                          eventId: this.state.eventProps.event.id,
                          comment: values.comment
                        })
                      });
                      actions.setSubmitting(false);
                      actions.resetForm();
                    }}
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
                  <Feedback
                    {...{
                      eventId: this.state.eventProps.event.id,
                      newFeedback: this.state.newEventFeedback
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

export default withRouter(EventDetail);
