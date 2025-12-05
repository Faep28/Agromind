import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../../services/user-service';

@Component({
  selector: 'app-home',
  standalone: false,
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home {

  // como traeriamos los datos?
  // simulando datos, luego quitarlos
  username: string = 'invitado';
  weatherSummary: string = 'Soleado, 28°C, sin lluvias por los próximos 3 días';
  irrigationRecommendation: string = 'Riego recomendado para mañana por 30 minutos.';
  cropStatus: string = 'Cultivo de maíz en buen estado. Requiere fertilización en 3 días.';

  // simular, debemos de colocar notificacoones en el backend
  alerts: any[] = [
    { title: 'Plaga detectada', description: 'Plaga leve detectada en el campo 1. Requiere atención.' },
    { title: 'Lluvias en camino', description: 'Se esperan lluvias fuertes en 48 horas. Ajuste el riego.' }
  ];

  showNotifications: boolean = false;
  
  
  constructor(private router: Router,
    public userService: UserService
    ) {
  }


  ngOnInit() {
    const storedUsername = localStorage.getItem("username");
    if (storedUsername) {
      this.username = storedUsername;
    }
  }

  markAsRead(alert: any) {
    alert.read = true;
    // aqui podemos implementar logica para marcar la alerta como leída (podriamos actualizar en el backend).
  }

  toggleNotifications() {
    this.showNotifications = !this.showNotifications;
  }
}
