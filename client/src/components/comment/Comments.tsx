import React, { useState, useEffect } from 'react';
import './Coments.scss';
import { getFeedbackRequest } from '../../api/feedback.service';
import { ErrorMessage } from '../../interfaces/ErrorMessage';
import { ErrorMessageComponent } from '../errorMessage/ErrorMessageComponent';

interface FeedbackProps {
  eventId: number;
}

interface Feedback {
  userId: {
    avatarUrl: string
    username: string
  };
  comment: string;
}

export const Feedback = (props: FeedbackProps): JSX.Element => {
  const componentUniKey = 'f33db4_00_';
  const [isLoading, setIsLoading] = useState();
  const [feedbackData, setFeedbackData] = useState([] as Feedback[]);
  const [error, setError] = useState();

  async function getFeedback(): Promise<void> {
    const response = await getFeedbackRequest(props.eventId);
    if (response.errorMessage) {
      setError({ ...response } as ErrorMessage);
    } else {
      setFeedbackData([...response] as Feedback[]);
    }
  }
  useEffect(() => {
    setIsLoading(true);
    getFeedback();
    console.debug('effect call', feedbackData);
    setIsLoading(false);
    return () => {};
  }, []);

  return (
    <div className='Comment-container'>
      <div className='Container__content'>
        {isLoading ? (
          <div className='spinner-border' role='status'>
            <span className='sr-only'>Loading...</span>
          </div>
        ) : null}
        {error ? (
          <ErrorMessageComponent {...{ errorMessage: [error.errorMessage] }} />
        ) : null}
        {feedbackData.map((feedback: any, index: number) => {
          console.debug(feedback);
          return (
            <div className='d-flex flex-row align-items-baseline justify-content-left'>
              <img
                className='rounded-avatar-sm'
                src={feedback.userId.avatarUrl}
              />
              <div
                className='toast show rounded w-100'
                key={componentUniKey + index}
              >
                <div className='toast-header'>
                  <strong>{feedback.userId.username}</strong>
                </div>
                <div className='toast-body'>{feedback.comment}</div>
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
};
