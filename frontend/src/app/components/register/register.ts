import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { UserService } from '../../services/user-service';
import { UserDto } from '../../models/user-dto';


@Component({
  selector: 'app-register',
  standalone: false,
  templateUrl: './register.html',
  styleUrl: './register.css',
})
export class Register {
  registerForm!: FormGroup;
  readonly ROLES_ADMITIDOS:string[] = [
    'ROLE_ADMIN', 'ROLE_USER', 'ROLE_ASSIST'
  ];


  constructor(
    private fb: FormBuilder, 
    private router: Router, 
    private userService: UserService, 
    private snack: MatSnackBar) {
  }

  ngOnInit(){
    this.CargarFormulario();
  }

  CargarFormulario(){
    this.registerForm = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', [Validators.required, Validators.minLength(6)]],
      authorities: ['', [Validators.required]]
    }); 
  }

  // Validación para asegurar que las contraseñas coinciden
  get confirmPassword() {
    return this.registerForm.get('confirmPassword');
  }

  // Método para manejar el registro
  onRegister() {
    if (this.registerForm.valid) {
      if (this.registerForm.value.password !== this.registerForm.value.confirmPassword) {
        this.snack.open("Las contraseñas no coinciden", "OK", { duration: 5000 });
        return;
      }

      // Crear el objeto UserDTO
      const userDTO: UserDto = {
        username: this.registerForm.value.username,
        password: this.registerForm.value.password,
        email: this.registerForm.value.email,
        authorities: 'ROLE_USER', 
        totalNotifications: null, 
        userId: null 
      };

      // Llamar al servicio para registrar el nuevo usuario
      this.userService.register(userDTO).subscribe({
        next: (response) => {
          console.log('Usuario registrado exitosamente:', response);
          this.router.navigate(['/login']);  // Redirigir al login después del registro
        },
        error: (error) => {
          this.snack.open("Error al registrar usuario: " + error.message, "OK", { duration: 5000 });
          console.error('Error al registrar usuario:', error);
        }
      });
    }
  }
}
