import { CanActivateFn } from '@angular/router';
import { UserService } from '../services/user-service';
import { inject } from '@angular/core';

export const autorizarAdminGuard: CanActivateFn = (route, state) => {
  let userService=inject(UserService);
  let permisos = userService.getAuthorities();  //"ROLE_ADMIN;ROLE_USER"
  if (permisos!=null && permisos.indexOf("ADMIN")>=0) {
    //console.log(permisos);
    return true;
  }

  return false;
};
