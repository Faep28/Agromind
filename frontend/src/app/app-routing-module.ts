import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Login } from './components/login/login';
import { Home } from './components/home/home';
import { autorizarLogeadoGuard } from './guards/autorizar-logeado-guard';
import { LandingPage } from './components/landing-page/landing-page';
import { Register } from './components/register/register';
import { RegistroCultivo } from './components/registro-cultivo/registro-cultivo';
import { RegistroNoticias } from './components/registro-noticias/registro-noticias';
import { autorizarAdminGuard } from './guards/autorizar-admin-guard';
import { ParcelaList } from './components/parcelas/parcela-list/parcela-list';

const routes: Routes = [
  {path:"", component:LandingPage }, // Cambiar a landing Page (hecho)  
  {path:"login", component:Login},
  {path:"home", component:Home, canActivate:[autorizarLogeadoGuard]},
  {path:"register", component:Register },
  {path:"parcelas", component:ParcelaList},
  {path:"cultivos", component:RegistroCultivo, canActivate:[autorizarLogeadoGuard]},
  {path:"noticias", component:RegistroNoticias, canActivate:[autorizarAdminGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
