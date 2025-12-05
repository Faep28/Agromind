import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { autorizarLogeadoGuard } from './autorizar-logeado-guard';

describe('autorizarLogeadoGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) => 
      TestBed.runInInjectionContext(() => autorizarLogeadoGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
