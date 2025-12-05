import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../../services/user-service';

@Component({
  selector: 'app-home',
  standalone: false,
  templateUrl: './home.html',
  styleUrls: ['./home.css'],
})
export class Home {

  // como traeriamos los datos?
  // simulando datos, luego quitarlos
  username: string = '';  // obtener de servicio.
  weatherSummary: string = 'Soleado, 28°C, sin lluvias por los próximos 3 días';
  irrigationRecommendation: string = 'Riego recomendado para mañana por 30 minutos.';
  cropStatus: string = 'Cultivo de maíz en buen estado. Requiere fertilización en 3 días.';

  // simular, debemos de colocar notificacoones en el backend
  alerts: any[] = [
    { title: 'Plaga detectada', description: 'Plaga leve detectada en el campo 1. Requiere atención.' },
    { title: 'Lluvias en camino', description: 'Se esperan lluvias fuertes en 48 horas. Ajuste el riego.' }
  ];

  showNotifications: boolean = false;

  constructor(private router: Router, private userService: UserService) { }

  ngOnInit() {
    // Intentar obtener el nombre del usuario desde el token JWT (si está disponible)
    const token = this.userService.getToken();
    if (token) {
      try {
        const payload = JSON.parse(atob(token.split('.')[1]));
        // Los campos pueden variar según el backend: pruebe 'name', 'sub', 'username', etc.
        this.username = payload.name || payload.sub || payload.username || '';
      } catch (e) {
        console.warn('No se pudo decodificar el token para obtener el nombre', e);
      }
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
