import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { autorizarAdminGuard } from './autorizar-admin-guard';

describe('autorizarAdminGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) => 
      TestBed.runInInjectionContext(() => autorizarAdminGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
