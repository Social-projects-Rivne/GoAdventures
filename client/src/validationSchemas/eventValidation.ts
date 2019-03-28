import * as Yup from 'yup';

const min = '2019-01-01';
const max = '2025-12-31';
const datereg = /^\\s*(3[01]|[12][0-9]|0?[1-9])\\.(1[012]|0?[1-9])\\.((?:19|20)\\d{2})\\s*$/;

export const EventSchema = Yup.object().shape({
  topic: Yup.string()
    .min(2, 'Too Short!')
    .max(35, 'Too Long!')
    .required('Required'),
  startDate: Yup.date()
    .default(new Date(min))
    .min(min, `startDate should be equal or later than ${min}`)
    .required('Start date required'),
  endDate: Yup.date()
    .default(new Date(max))
    .max(new Date(max), `endDate should be equal or earlier than ${max}`)
    .required('End Date required')
});
