import { HttpInterceptorFn } from '@angular/common/http';
import { UserService } from '../services/user-service';
import { inject } from '@angular/core';

export const autorizacionInterceptor: HttpInterceptorFn = (req, next) => {

  let userService=inject(UserService);
    const token = userService.getToken();
    if (token) {
      const nuevaReq = req.clone({
        headers:req.headers.set('Authorization',"Bearer "+token)
      })

      return next(nuevaReq);
    }
    return next(req);
};
