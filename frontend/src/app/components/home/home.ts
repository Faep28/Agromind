import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../../services/user-service';
import { NotificacionesService } from '../../services/notificaciones-service';
import { Notificacion } from '../../models/notificacion';

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

  // notificaciones reales
  notificaciones: Notificacion[] = [];
  notificacionesMostradas: Notificacion[] = [];

  showNotifications: boolean = false;

  constructor(private router: Router, private userService: UserService, private notiService: NotificacionesService) { }

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

    // cargar notificaciones no leídas para mostrar en home
    this.loadUnreadNotifications();

    // suscribirse a cambios desde el servicio para refrescar
    this.notiService.obtenerActualizaciones$().subscribe(() => {
      this.loadUnreadNotifications();
    });
  }
 
  loadUnreadNotifications() {
    const userId = this.userService.getUserId();
    if (!userId || userId === 0) {
      this.notificaciones = [];
      this.notificacionesMostradas = [];
      return;
    }

    this.notiService.listByUser(userId).subscribe({
      next: (list) => {
        this.notificaciones = list || [];
        // ordenar: primero no leídas, luego leídas. Mostrar hasta 2 en el home.
        const sorted = [...this.notificaciones].sort((a, b) => {
          if (a.esLeido === b.esLeido) return 0;
          return a.esLeido ? 1 : -1;
        });
        this.notificacionesMostradas = sorted.slice(0, 2);
      },
      error: (err) => {
        console.error('Error cargando notificaciones del usuario:', err);
        this.notificaciones = [];
        this.notificacionesMostradas = [];
      }
    });
  }
  markAsRead(n: Notificacion) {
    const updated: Notificacion = { ...n, esLeido: true };
    this.notiService.update(n.id, updated).subscribe({
      next: () => {
        // no eliminamos localmente; solo cerramos el panel y notificamos para refrescar
        this.showNotifications = false;
        this.notiService.notificarCambios();
      },
      error: (err) => {
        console.error('Error marcando notificación como leída:', err);
      }
    });
  }

  toggleNotifications() {
    this.showNotifications = !this.showNotifications;
  }
}
