import { CanActivateFn } from '@angular/router';
import { UserService } from '../services/user-service';
import { inject } from '@angular/core';

export const autorizarLogeadoGuard: CanActivateFn = (route, state) => {
  let userService=inject(UserService);
  return userService.isLogged();
};
