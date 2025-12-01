<<<<<<< HEAD
import { Injectable } from '@angular/core';
import { TokenDTO } from '../models/token-dto';
import { HttpClient } from '@angular/common/http';
import { User } from '../models/user';
=======
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../models/user';
import { TokenDTO } from '../models/tokenDTO';
>>>>>>> a609277653a612f3152676da2483e697db11e5dd
import { tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UserService {
<<<<<<< HEAD
  
  ruta_servidor:string = "http://localhost:8080/upc";
=======

  ruta_servidor:string = "http://localhost:8080/api/users";
>>>>>>> a609277653a612f3152676da2483e697db11e5dd
  recurso:string ="users";

  constructor(private http:HttpClient){}

<<<<<<< HEAD
  
  login(user:User){                        
    return this.http.post<TokenDTO>(this.ruta_servidor+"/"+this.recurso+"/"+"login", user).pipe(
      tap( (respuesta:TokenDTO)=>{
                localStorage.setItem("jwtToken",respuesta.jwtToken);
                localStorage.setItem("user_id",respuesta.id.toString());
                localStorage.setItem("authorities",respuesta.authorities);
      }
      )
    )
  }

  logout(){
    if (typeof localStorage !== "undefined" ) {
        localStorage.clear();
    }    
  }

  getToken(){
    if (typeof localStorage !== "undefined" ) {
=======

  // Método de login
  login(user: User) {
    return this.http.post<TokenDTO>(this.ruta_servidor + "/login", user).pipe(
      tap((respuesta: TokenDTO) => {
        localStorage.setItem("jwtToken", respuesta.jwtToken);
        localStorage.setItem("user_id", respuesta.id.toString());
        localStorage.setItem("authorities", respuesta.authorities);
      })
    );
  }

   // Método de logout
  logout() {
    if (typeof localStorage !== "undefined") {
      localStorage.clear();
    }
  }

  // Obtener el token JWT
  getToken() {
    if (typeof localStorage !== "undefined") {
>>>>>>> a609277653a612f3152676da2483e697db11e5dd
      return localStorage.getItem("jwtToken");
    }
    return null;
  }

<<<<<<< HEAD
  getAuthorites(){
    if (typeof localStorage !== "undefined" ) {
=======
  // Obtener los authorities del usuario
  getAuthorities() {
    if (typeof localStorage !== "undefined") {
>>>>>>> a609277653a612f3152676da2483e697db11e5dd
      return localStorage.getItem("authorities");
    }
    return null;
  }

<<<<<<< HEAD
=======
  // Obtener el ID del usuario
>>>>>>> a609277653a612f3152676da2483e697db11e5dd
  getUserId(){
    if (typeof localStorage !== "undefined" ) {
      let lsUserId = localStorage.getItem("user_id");
      if (!lsUserId == null) {
        return parseInt(localStorage.getItem("user_id")!.toString());
      }   
      return 0;   
    }
    return 0;
  }

<<<<<<< HEAD

  isLogged(){
    if (this.getToken()==null || this.getToken()==""){
=======
   // Verificar si el usuario está logueado
  isLogged() {
    if (this.getToken() == null || this.getToken() == "") {
>>>>>>> a609277653a612f3152676da2483e697db11e5dd
      return false;
    }
    return true;
  }

<<<<<<< HEAD
/*
  new(faculty:Faculty){
    return this.http.post<Faculty>(this.ruta_servidor+"/"+this.recurso, faculty);
  }
*/

}
=======
  
}
>>>>>>> a609277653a612f3152676da2483e697db11e5dd
