import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../models/user';
import { TokenDTO } from '../models/tokenDTO';
import { tap } from 'rxjs';
import { UserDTO } from '../models/userDTO';
import { VARIABLES } from '../shared/values';


@Injectable({
  providedIn: 'root',
})
export class UserService {

  ruta_servidor:string = "http://localhost:8080/api/users";
  recurso:string ="users";


  constructor(
    private http:HttpClient
  ){
  }

  // Método de login
  login(user: User) {
    return this.http.post<TokenDTO>(this.ruta_servidor + "/login", user).pipe(
      tap((respuesta: TokenDTO) => {
        localStorage.setItem("jwtToken", respuesta.jwtToken);
        localStorage.setItem("user_id", respuesta.id.toString());
        localStorage.setItem("authorities", respuesta.authorities);

        this.getUserByName(+localStorage.getItem("user_id")!).subscribe({
          next:(data:User) => {
            localStorage.setItem("username", data.username);
          },
          error:(err:any) => {
            console.log("Console: ", err);
          }
        });

      })
    );
  }


  register(newUser:UserDTO) {
    return this.http.post<UserDTO>(this.ruta_servidor + "/register", newUser);
  }

   // Método de logout
  logout() {
    if (typeof localStorage !== "undefined") {
      localStorage.clear();
    }
  }

  // Obtener el token JWT
  getToken(): string | null {
    return localStorage.getItem('jwtToken');  // Recupera el token almacenado en el localStorage
  }

  // Obtener los authorities del usuario
  getAuthorities() {
    if (typeof localStorage !== "undefined") {
      return localStorage.getItem("authorities");
    }
    return null;
  }

  // Obtener el ID del usuario
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

  // O
  getUserByName(id:number) {
    return this.http.get<User>(this.ruta_servidor + "/"+id.toString());
  }

   // Verificar si el usuario está logueado
  isLogged() {
    if (this.getToken() == null || this.getToken() == "") {
      
      // podramos implementar para q redirija a la landing
      return false;
    }
    return true;
  }

  
}
