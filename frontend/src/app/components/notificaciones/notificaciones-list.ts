import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { NotificacionesService } from '../../services/notificaciones-service';
import { UserService } from '../../services/user-service';
import { Notificacion } from '../../models/notificacion';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-notificaciones-list',
  standalone: false,
  templateUrl: './notificaciones-list.html',
  styleUrls: ['./notificaciones-list.css']
})
export class NotificacionesList {
  notificaciones: Notificacion[] = [];

  constructor(private router: Router, private notiService: NotificacionesService, private userService: UserService, private snack: MatSnackBar) {}

  ngOnInit() {
    this.loadAll();
    this.notiService.obtenerActualizaciones$().subscribe(() => this.loadAll());
  }

  loadAll() {
    const userId = this.userService.getUserId();
    if (!userId || userId === 0) {
      this.notificaciones = [];
      return;
    }
    console.log('NotificacionesList: solicitando notificaciones para userId=', userId);
    this.notiService.listByUser(userId).subscribe({
      next: (list) => {
        console.log('NotificacionesList: respuesta list.length=', (list && list.length) || 0, list);
        this.notificaciones = list || [];
      },
      error: (err) => {
        console.error('Error al cargar notificaciones:', err);
        this.notificaciones = [];
      }
    });
  }

  marcarComoLeida(n: Notificacion) {
    if (!n || !n.id) return;
    const updated: Notificacion = { ...n, esLeido: true };
    this.notiService.update(n.id, updated).subscribe({
      next: () => {
        this.snack.open('Notificación marcada como leída', 'Cerrar', { duration: 2000 });
        this.notiService.notificarCambios();
      },
      error: (err) => {
        console.error('Error marcando como leída:', err);
        this.snack.open('Error al marcar como leída', 'Cerrar', { duration: 3000 });
      }
    });
  }

  eliminar(n: Notificacion) {
    if (!n || !n.id) return;
    if (!confirm('¿Eliminar esta notificación?')) return;
    this.notiService.delete(n.id).subscribe({
      next: () => {
        this.snack.open('Notificación eliminada', 'Cerrar', { duration: 2000 });
        this.notiService.notificarCambios();
      },
      error: (err) => {
        console.error('Error eliminando notificación:', err);
        this.snack.open('Error al eliminar', 'Cerrar', { duration: 3000 });
      }
    });
  }

  volver() {
    this.router.navigate(['/home']);
  }
}
