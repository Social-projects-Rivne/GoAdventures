import { Observable } from 'rxjs';
export type RouteGuardResultType =
  | boolean
  | Promise<boolean>
  | Observable<boolean>;

/**
 * @export
 * @interface RouteGuard
 */
export interface Guard {
  shouldRoute: () => RouteGuardResultType;
}
