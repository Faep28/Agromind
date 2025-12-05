import { NgModule, provideBrowserGlobalErrorListeners } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing-module';
import { App } from './app';
import { Home } from './components/home/home';
import { Cultivos } from './components/cultivos/cultivos';
import { LandingPage } from './components/landing-page/landing-page';
import { Login } from './components/login/login';
import { Register } from './components/register/register';
import { RegistroCultivo } from './components/registro-cultivo/registro-cultivo';
import { RegistroNoticias } from './components/registro-noticias/registro-noticias';
import { Header } from './components/header/header';
import { HttpClientModule } from '@angular/common/http';
import { MaterialModule } from './modules/material/material-module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ParcelaList } from './components/parcelas/parcela-list/parcela-list';
import { AddEditParcela } from './components/parcelas/add-edit-parcela/add-edit-parcela';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgxChartsModule } from '@swimlane/ngx-charts';

@NgModule({
  declarations: [
    App,
    Home,
    Cultivos,
    LandingPage,
    Login,
    Register,
    RegistroCultivo,
    RegistroNoticias,
    Header,
    ParcelaList,
    AddEditParcela
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    NgxChartsModule,
    BrowserAnimationsModule
  ],
  providers: [
    provideBrowserGlobalErrorListeners()
  ],
  bootstrap: [App]
})
export class AppModule { }
