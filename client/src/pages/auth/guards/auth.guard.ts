import { Guard } from './auth.guard.interface';
class AuthGurad  implements Guard {
    public shouldRoute() {
        const resultFromSyncApiCall = true;
        return resultFromSyncApiCall;
      }
}

export default AuthGurad;
