import { Field, FieldProps, Form, Formik, FormikProps } from 'formik';
import React, { Component } from 'react';
import { MdDone } from 'react-icons/md';
import { Comments, Gallery, SettingsPanel } from '../../../components';
import { EventDto } from '../../../interfaces/Event.dto';
import { commentsSchema } from '../../../validationSchemas/commentValidation';
import './EventDetail.scss';

interface FormValue {
  comment: string;
}

export class EventsDetail extends Component<any, any> {
  constructor(props: EventDto) {
    super(props);
    this.state = {
      eventProps: { ...this.props.routerProps.location.state }
    };
  }

  public render() {
    console.debug(this.props);
    return (
      <div className='container-fluid EventDetail'>
        <div className='row'>
          <div className='col-6'>
            <Gallery {...this.state.eventProps.gallery} />
          </div>
          <div className='col-6'>
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
              <button type='button' className='btn btn-success'>
                Edit
              </button>
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
