import { Component } from '@angular/core';
import { UserService } from '../services/user-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  standalone: false,
  templateUrl: './header.html',
  styleUrl: './header.css',
})
export class Header {

  isAdmin = false;
  isUser  = false;

  constructor ( private userService: UserService, private router:Router ){}

  ngOnInit(): void {
    this.cargarRoles();
  }

  cargarRoles() {
    const permisos = this.userService.getAuthorities() ?? ''; // "ROLE_ADMIN;ROLE_USER"

    this.isAdmin = permisos.includes('ROLE_ADMIN');
    // Considero user si tiene ROLE_USER o si es admin
    this.isUser  = permisos.includes('ROLE_USER') || this.isAdmin;
  }

  Logout(){
      this.userService.logout();
      this.router.navigate(["/login"]);
  }

}
