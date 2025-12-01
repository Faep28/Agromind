import { NgModule, provideBrowserGlobalErrorListeners } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing-module';
import { App } from './app';
import { Login } from './components/login/login';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { provideNativeDateAdapter } from '@angular/material/core';
import { MaterialModule } from './modules/material/material-module';

@NgModule({
  declarations: [
    App,
    Login
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
    provideNativeDateAdapter()
  ],
  bootstrap: [App]
})
export class AppModule { }
