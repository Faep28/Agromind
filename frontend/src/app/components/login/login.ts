import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from '../../services/user-service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { User } from '../../models/user';
import { TokenDTO } from '../../models/tokenDTO';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {

  loginForm!:FormGroup;

  constructor (private formBuilder:FormBuilder, private userService: UserService,  private snack:MatSnackBar, private router:Router ){}

  ngOnInit(){
    this.CargarFormulario();
  }


  CargarFormulario(){
    this.loginForm = this.formBuilder.group({
      id: [""],
      username: ["", [Validators.required, Validators.minLength(3)]],
      password: ["", [Validators.required, Validators.minLength(3)]],
      enabled:  [""]
    });  
  }

   Login(){


    const user:User={
        id: 0,
        username: this.loginForm.get("username")?.value,
        password: this.loginForm.get("password")?.value,
        enabled: true,
    }

    this.userService.login(user).subscribe({
      next:(data:TokenDTO)=>{
        //console.log(data);
        this.router.navigate(["/home"]);
      },
      error:(http_error:any)=>{
                this.snack.open("ERROR AL LOGEAR: " + http_error.error.message,"OK",{duration:5000});
                console.log(http_error);                  
              }
    })
  }

}
