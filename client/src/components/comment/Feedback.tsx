import React, { useState, useEffect } from 'react';
import './Feedback.scss';
import { getFeedbackRequest } from '../../api/feedback.service';
import { ErrorMessage } from '../../interfaces/ErrorMessage';
import { ErrorMessageComponent } from '../errorMessage/ErrorMessageComponent';

interface FeedbackProps {
  eventId: number;
  newFeedback: FeedbackSchema;
}

interface FeedbackSchema {
  userId: {
    avatarUrl: string
    username: string
  };
  comment: string;
}

export const Feedback = (props: FeedbackProps): JSX.Element => {
  const componentUniKey = 'f33db4_00_';
  const [isLoading, setIsLoading] = useState();
  const [feedbackData, setFeedbackData] = useState([] as FeedbackSchema[]);
  const [error, setError] = useState();

  async function getFeedback(): Promise<void> {
    const response = await getFeedbackRequest(props.eventId);
    console.debug(response.content);
    if (response.errorMessage) {
      setError({ ...response } as ErrorMessage);
    } else {
      setFeedbackData([...response.content] as FeedbackSchema[]);
    }
  }

  useEffect(() => {
    if (Object.keys(props.newFeedback).length > 0) {
      setFeedbackData([...feedbackData, props.newFeedback]);
    }
    return () => {};
  }, [props.newFeedback]);

  useEffect(() => {
    setIsLoading(true);
    getFeedback();
    setIsLoading(false);
    return () => {};
  }, []);

  return (
    <div className='Feedback-container'>
      <div className='Feedback__content'>
        {isLoading ? (
          <div className='spinner-border' role='status'>
            <span className='sr-only'>Loading...</span>
          </div>
        ) : null}
        {error ? (
          <ErrorMessageComponent {...{ errorMessage: [error.errorMessage] }} />
        ) : null}
        {feedbackData.map((feedback: any, index: number) => {
          return (
            <div
              key={componentUniKey + index}
              className='d-flex flex-row align-items-baseline justify-content-left'
            >
              <img
                className='rounded-avatar-sm'
                src={feedback.userId.avatarUrl}
              />
              <div className='toast show rounded w-100'>
                <div className='toast-header'>
                  <strong>{feedback.userId.username}</strong>
                </div>
                <div className='toast-body'>
                  <span>{feedback.comment}</span>
                </div>
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
};
