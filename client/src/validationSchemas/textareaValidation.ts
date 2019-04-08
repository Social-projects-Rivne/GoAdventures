import * as Yup from 'yup';

export const textAreaSchema = Yup.object().shape({
  textarea: Yup.string()
    .min(10, 'Description must be minimum 35 characters')
    .max(300, 'Description can be maximum 300 charactres')
    .required('Description i srequired')
});
