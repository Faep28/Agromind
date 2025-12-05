import { HttpInterceptorFn } from '@angular/common/http';

export const autorizacionInterceptor: HttpInterceptorFn = (req, next) => {
  return next(req);
};
