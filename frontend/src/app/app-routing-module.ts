import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Login } from './components/login/login';
<<<<<<< HEAD

const routes: Routes = [
  { path:"", component:Login }
=======
import { Home } from './components/home/home';
import { autorizarLogeadoGuard } from './guards/autorizar-logeado-guard';

const routes: Routes = [
  {path:"", component:Login},  
  {path:"login", component:Login},
  {path:"home", component:Home, canActivate:[autorizarLogeadoGuard]}

>>>>>>> a609277653a612f3152676da2483e697db11e5dd
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
