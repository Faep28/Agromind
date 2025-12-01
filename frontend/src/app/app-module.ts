import { NgModule, provideBrowserGlobalErrorListeners } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing-module';
import { App } from './app';
import { Login } from './components/login/login';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { provideNativeDateAdapter } from '@angular/material/core';
<<<<<<< HEAD
import { MaterialModule } from './modules/material/material-module';
=======
import { Login } from './components/login/login';
import { Home } from './components/home/home';
import { autorizacionInterceptor } from './interceptors/autorizacion-interceptor';
import { Header } from './header/header';
>>>>>>> a609277653a612f3152676da2483e697db11e5dd

@NgModule({
  declarations: [
    App,
<<<<<<< HEAD
    Login
=======
    Login,
    Home,
    Header
>>>>>>> a609277653a612f3152676da2483e697db11e5dd
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    MaterialModule
    // NgxChartsModule //falta
  ],
  providers: [
    provideBrowserGlobalErrorListeners(),
<<<<<<< HEAD
    provideNativeDateAdapter()
=======

    provideNativeDateAdapter(),
    provideHttpClient(
      withInterceptors([autorizacionInterceptor])
    )
    
>>>>>>> a609277653a612f3152676da2483e697db11e5dd
  ],
  bootstrap: [App]
})
export class AppModule { }
