import * as Yup from 'yup';

export const textareSchema = Yup.object().shape({
  textarea: Yup.string()
    .min(35, 'Description must be minimum 35 characters')
    .max(300, 'Description can be maximum 300 charactres')
    .required('Description i srequired')
});
