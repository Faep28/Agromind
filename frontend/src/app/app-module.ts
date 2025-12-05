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
import { RegistroCultivo } from './components/registro-cultivo/registro-cultivo';
import { Cultivos } from './components/cultivos/cultivos';
import { RegistroNoticias } from './components/registro-noticias/registro-noticias';
import { ParcelaList } from './components/parcelas/parcela-list/parcela-list';
import { AddEditParcela } from './components/parcelas/add-edit-parcela/add-edit-parcela';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { RecomendacionesComponent } from './components/recomendaciones/recomendaciones.component'; 

@NgModule({
  declarations: [
    App,
    Login, 
    Home, 
    Header,
    LandingPage,
    Register
    ,RegistroCultivo, RegistroNoticias, ParcelaList, Cultivos, AddEditParcela
    ,RecomendacionesComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    MaterialModule,
    NgxChartsModule
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
