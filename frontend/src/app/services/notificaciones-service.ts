import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Notificacion } from '../models/notificacion';
import { Observable, Subject } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class NotificacionesService {

  private apiUrl = `${environment.apiUrl}/notificaciones`;

  constructor(private http: HttpClient) {}

   // Listar todas las notificaciones
  listAll(): Observable<Notificacion[]> {
    return this.http.get<Notificacion[]>(`${this.apiUrl}/list`);
  }
  
  // Listar notificaciones por usuario
  listByUser(userId: number): Observable<Notificacion[]> {
    return this.http.get<Notificacion[]>(`${this.apiUrl}/user/${userId}`);
  }

  // Insertar nueva notificación
  insert(notificacion: Notificacion): Observable<Notificacion> {
    return this.http.post<Notificacion>(`${this.apiUrl}/insert`, notificacion);
  }

  // Actualizar notificación (incluye marcar como leída)
  update(id: number, notificacion: Notificacion): Observable<Notificacion> {
    return this.http.put<Notificacion>(`${this.apiUrl}/update/${id}`, notificacion);
  }

  // Eliminar notificación
  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/delete/${id}`);
  }

  // Subject para notificar cambios en notificaciones
  private notificacionesActualizadas$ = new Subject<void>();

  notificarCambios() {
    this.notificacionesActualizadas$.next();
  }

  obtenerActualizaciones$() {
    return this.notificacionesActualizadas$.asObservable();
  }
}
