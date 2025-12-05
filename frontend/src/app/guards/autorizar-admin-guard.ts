import { CanActivateFn } from '@angular/router';

export const autorizarAdminGuard: CanActivateFn = (route, state) => {
  return true;
};
