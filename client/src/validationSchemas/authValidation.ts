import * as Yup from 'yup';

export const SignupSchema = Yup.object().shape({
  email: Yup.string()
    .email('Invalid email')
    .required('Required'),
  fullname: Yup.string()
    .min(2, 'Too Short!')
    .max(35, 'Too Long!')
    .required('Required'),
  password: Yup.string()
    .max(18, 'Password is to long')
    .required('Required')
    .min(5, 'Password is to short'),
  confirmPassword: Yup.string()
    .oneOf([Yup.ref('password')], 'Passwords not match')
    .max(18, 'Password is to long')
    .required('Confirm password is required')
    .min(5, 'Password is to short')
});

export const SigninSchema = Yup.object().shape({
  email: Yup.string()
    .email('Invalid email')
    .required('Required'),
  password: Yup.string()
    .max(18, 'Password is to long')
    .required('Required')
    .min(5, 'Password is to short')
});

const phoneRegExp = /^((\\+[1-9]{1,4}[ \\-]*)|(\\([0-9]{2,3}\\)[ \\-]*)|([0-9]{2,4})[ \\-]*)*?[0-9]{3,4}?[ \\-]*[0-9]{3,4}?$/;
export const editProfileSchema = Yup.object().shape({
  email: Yup.string().email('Invalid email'),
  fullname: Yup.string()
    .min(2, 'Too Short!')
    .max(35, 'Too Long!'),
  username: Yup.string()
    .min(4, 'Too Short!')
    .max(10, 'Too Long!'),
  location: Yup.string()
    .min(2, 'Too Short!')
    .max(35, 'Too Long!'),
  phone: Yup.string().matches(phoneRegExp, 'Phone number is not valid'),

  password: Yup.string()
    .required("Required"),
  newPassword: Yup.string()
    .max(18, 'Password is to long')
    .notOneOf([Yup.ref('password')], 'The new password must be different from the previous one')
    .min(5, 'Password is to short'),
  confirmPassword: Yup.string()
    .oneOf([Yup.ref('newPassword')], 'Passwords not match!')
    .max(18, 'Password is to long')
    .min(5, 'Password is to short')
});
