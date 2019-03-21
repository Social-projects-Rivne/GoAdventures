import * as Yup from 'yup';

export const SignupSchema = Yup.object().shape({
  email: Yup.string()
    .email('Invalid email')
    .required('Required'),
  fullName: Yup.string()
    .min(2, 'Too Short!')
    .max(35, 'Too Long!')
    .required('Required'),
  password: Yup.string()
    .max(18, 'Password is to long')
    .required('Required')
    .min(5, 'Password is to short'),
  confirmPassword: Yup.string()
    .oneOf([Yup.ref('password')], 'Passwords are not the same!')
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

export const editProfileSchema = Yup.object().shape({
  email: Yup.string()
    .email('Invalid email')
    .required('Required'),
  fullName: Yup.string()
    .min(2, 'Too Short!')
    .max(35, 'Too Long!')
    .required('Required'),
  password: Yup.string()
    .max(18, 'Password is to long')
    .required('Required')
    .min(5, 'Password is to short'),
  newPassword: Yup.string()
    .max(18, 'Password is to long')
    .required('Required')
    .min(5, 'Password is to short'),
  confirmPassword: Yup.string()
    .oneOf([Yup.ref('newPassword')], 'Passwords are not the same!')
    .max(18, 'Password is to long')
    .required('Confirm password is required')
    .min(5, 'Password is to short')
});
