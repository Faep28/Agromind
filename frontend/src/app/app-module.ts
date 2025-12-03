import { NgModule, provideBrowserGlobalErrorListeners } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing-module';
import { App } from './app';
import { HttpClientModule, provideHttpClient, withInterceptors } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { provideNativeDateAdapter } from '@angular/material/core';
import { Login } from './components/login/login';
import { Home } from './components/home/home';
import { autorizacionInterceptor } from './interceptors/autorizacion-interceptor';
import { Header } from './header/header';
import { MaterialModule } from './modules/material/material-module';
import { LandingPage } from './components/landing-page/landing-page';
import { Register } from './components/register/register';

@NgModule({
  declarations: [
    App,
    Login,
    Home,
    Header,
    LandingPage,
    Register
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

    provideNativeDateAdapter(),
    provideHttpClient(
      withInterceptors([autorizacionInterceptor])
    )
    
  ],
  bootstrap: [App]
})
export class AppModule { }
