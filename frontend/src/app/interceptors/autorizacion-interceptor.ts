import { HttpInterceptorFn } from '@angular/common/http';
import { UserService } from '../services/user-service';
import { inject } from '@angular/core';

export const autorizacionInterceptor: HttpInterceptorFn = (req, next) => {

  let userService=inject(UserService);
    const token = userService.getToken();
    if (token) {
      console.log(`🔐 [${req.method}] ${req.url}`);
      console.log('Token:', token.substring(0, 30) + '...');
      console.log('Authorities:', localStorage.getItem('authorities'));
      
      const nuevaReq = req.clone({
        headers:req.headers.set('Authorization',"Bearer "+token)
      })

      return next(nuevaReq);
    }
    console.warn('⚠️ No token found for request:', req.url);
    return next(req);
};
