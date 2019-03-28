import * as Yup from 'yup';

export const commentsSchema = Yup.object().shape({
  comment: Yup.string()
    .min(2, 'Too Short!')
    .max(300, 'Too Long!')
    .required('You need to type something!')
});
