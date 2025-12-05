import { CanActivateFn } from '@angular/router';

export const autorizarLogeadoGuard: CanActivateFn = (route, state) => {
  return true;
};
