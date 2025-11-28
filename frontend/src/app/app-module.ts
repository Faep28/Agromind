import { NgModule, provideBrowserGlobalErrorListeners } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing-module';
import { App } from './app';
import { HttpClientModule, provideHttpClient, withInterceptors } from '@angular/common/http';
import { MaterialModule } from './modules/material-module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { provideNativeDateAdapter } from '@angular/material/core';
import { Login } from './components/login/login';
import { Home } from './components/home/home';
import { autorizacionInterceptor } from './interceptors/autorizacion-interceptor';
import { Header } from './header/header';

@NgModule({
  declarations: [
    App,
    Login,
    Home,
    Header
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    //NgxChartsModule, falta
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
