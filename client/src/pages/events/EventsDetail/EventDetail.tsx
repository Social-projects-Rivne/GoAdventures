import { AxiosResponse } from 'axios';
import { Field, FieldProps, Form, Formik, FormikProps } from 'formik';
import React, { Component } from 'react';
import { MdDone } from 'react-icons/md';
import { RouteComponentProps } from 'react-router';
import { deleteEvent, isOwner } from '../../../api/event.service';
import { Comments, Gallery, SettingsPanel } from '../../../components';
import { commentsSchema } from '../../../validationSchemas/commentValidation';
import './EventDetail.scss';

interface EventDetailProps {
  routerProps: RouteComponentProps;
}

interface FormValue {
  comment: string;
}

export class EventDetail extends Component<EventDetailProps, any> {
  constructor(props: EventDetailProps) {
    super(props);
    this.state = {
      eventProps: { ...this.props.routerProps.location.state }
    };

    this.handleDelete = this.handleDelete.bind(this);
  }

  public componentDidMount() {
    isOwner(this.state.eventProps.id).then(
      (res: AxiosResponse): any => {
        console.log(res.status + ' | ' + res.statusText);
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
    deleteEvent(this.state.eventProps.id).then(
      (res: AxiosResponse): any => {
        console.log(res.status);
        if (res.status >= 200 && res.status <= 300) {
          this.props.routerProps.history.push('/profile');
        } else {
        }
      }
    );
  }

  public render() {
    console.debug(this.props);
    return (
      <div className='container EventDetail'>
        <div className='row'>
          <div className='col-12 col-sm-12 col-md-6 col-lg-6 col-xl-6'>
            <Gallery {...this.state.eventProps.gallery} />
          </div>
          <div className='col-12 col-sm-12 col-md-6 col-lg-6 col-xl-6'>
            <div className='jumboton jumbotron-fluid'>
              <SettingsPanel>
                {{
                  left: (
                    <div>
                      <div className='d-flex flex-row align-content-center'>
                        <img
                          className='rounded-avatar-sm'
                          src='https://www.kidzone.ws/animal-facts/whales/images/beluga-whale-3.jpg'
                        />
                        <h2>{this.state.eventProps.topic}</h2>
                      </div>
                      <p>Friday,19 April 2019</p>
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
              <span className='lead'>{this.state.eventProps.description}</span>
              <hr className='my-4' />
              <p>
                Start: {this.state.eventProps.startDate} -{' '}
                {this.state.eventProps.endDate}
              </p>
              <hr className='my-4' />
              <div className='map'>
                <h2>Location and Destination points</h2>
                <div>MAP</div>
                {this.state.eventProps.location}
              </div>

              {this.state.isOwner ? (
                <div>
                  <button type='button' className='btn btn-success'>
                    Edit
                  </button>
                  <button
                    onClick={this.handleDelete}
                    type='button'
                    className='btn btn-danger'
                  >
                    Delete
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
                    onSubmit={() => {
                      console.debug('request');
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
