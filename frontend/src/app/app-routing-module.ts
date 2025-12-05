import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Login } from './components/login/login';
import { Home } from './components/home/home';
import { autorizarLogeadoGuard } from './guards/autorizar-logeado-guard';
import { LandingPage } from './components/landing-page/landing-page';
import { Register } from './components/register/register';
import { RegistroCultivo } from './components/registro-cultivo/registro-cultivo';
import { Cultivos } from './components/cultivos/cultivos';
import { ParcelaList } from './components/parcelas/parcela-list/parcela-list';
import { RegistroNoticias } from './components/registro-noticias/registro-noticias';
import { autorizarAdminGuard } from './guards/autorizar-admin-guard';
import { AddEditParcela } from './components/parcelas/add-edit-parcela/add-edit-parcela';


const routes: Routes = [
  {path:"", component:LandingPage }, // Cambiar a landing Page (hecho)  
  {path:"login", component:Login},
  {path:"home", component:Home, canActivate:[autorizarLogeadoGuard]},
  {path:"register", component:Register },
  {path:"registro-cultivo", component:RegistroCultivo, canActivate:[autorizarLogeadoGuard]},
  {path:"parcelas", component:ParcelaList, canActivate:[autorizarLogeadoGuard]},
  {path:"cultivos", component:Cultivos, canActivate:[autorizarLogeadoGuard]},
  {path:"parcelas-add", component:AddEditParcela, canActivate:[autorizarLogeadoGuard]},
  {path:"noticias", component:RegistroNoticias, canActivate:[autorizarAdminGuard]},
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

