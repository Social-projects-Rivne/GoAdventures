import React, { useState, useEffect } from 'react';
import './Coments.scss';
import { getFeedbackRequest } from '../../api/feedback.service';
import { ErrorMessage } from '../../interfaces/ErrorMessage';

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
  const [isLoading, setIsLoading] = useState();
  const [feedbackData, setFeedbackData] = useState([
    {
      userId: {
        avatarUrl: '',
        username: ''
      },
      comment: ''
    }
  ] as Feedback[]);
  const [error, setError] = useState();

  useEffect(() => {
    console.debug(props);
    setIsLoading(true);
    async function getFeedback(): Promise<void> {
      const response = await getFeedbackRequest(props.eventId);
      if (response.hasOwnProperty('errorMessage')) {
        setError({ ...response } as ErrorMessage);
      } else {
        setFeedbackData({ ...response } as Feedback[]);
      }
    }
    getFeedback();
    setIsLoading(false);
    return () => {};
  }, []);

  return (
    <div className='Comment'>
      <div className='row'>
        <div className=''>
          <img
            className='rounded-avatar-sm'
            src={feedbackData[0].userId.avatarUrl}
          />
        </div>
        <div className='col-10'>
          <div className='Comment__wrapper'>
            <div className='toast-header'>
              <strong>{feedbackData[0].userId.username}</strong>
            </div>
            <div className='toast-body'>{feedbackData[0].comment}</div>
          </div>
        </div>
      </div>
    </div>
  );
};
