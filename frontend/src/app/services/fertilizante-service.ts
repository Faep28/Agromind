import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CultivoFertilizante } from '../models/cultivo-fertilizante';
import { Fertilizante } from '../models/fertilizante';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class FertilizanteService {
  private apiUrl = `${environment.apiUrl}/notificaciones`;

  constructor(private http: HttpClient) {}

  // Obtener todas las relaciones cultivo-fertilizante de un cultivo específico
  getFertilizantesByCultivo(cultivoId: number): Observable<CultivoFertilizante[]> {
    return this.http.get<CultivoFertilizante[]>(`${this.apiUrl}/cultivo-fertilizante/cultivo/${cultivoId}`);
  }

  // Listar todos los fertilizantes disponibles
  listAll(): Observable<Fertilizante[]> {
    return this.http.get<Fertilizante[]>(`${this.apiUrl}/fertilizantes/list`);
  }

  // Crear un nuevo fertilizante
  create(fertilizante: Partial<Fertilizante>): Observable<Fertilizante> {
    return this.http.post<Fertilizante>(`${this.apiUrl}/fertilizantes/insert`, fertilizante);
  }

  // Actualizar un fertilizante
  update(id: number, fertilizante: Partial<Fertilizante>): Observable<Fertilizante> {
    return this.http.put<Fertilizante>(`${this.apiUrl}/fertilizantes/update/${id}`, fertilizante);
  }

  // Eliminar un fertilizante
  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/fertilizantes/delete/${id}`);
  }

  // Asignar fertilizante a un cultivo (crear relación cultivo-fertilizante)
  asignarFertilizanteACultivo(cultivoId: number, fertilizanteId: number, data: { fechaAplicacion: string, cantidad: number }): Observable<CultivoFertilizante> {
    return this.http.post<CultivoFertilizante>(`${this.apiUrl}/cultivo-fertilizante/insert/${cultivoId}/${fertilizanteId}`, data);
  }

  // Actualizar relación cultivo-fertilizante
  updateRelacion(id: number, data: any): Observable<CultivoFertilizante> {
    return this.http.put<CultivoFertilizante>(`${this.apiUrl}/cultivo-fertilizante/update/${id}`, data);
  }

  // Eliminar relación cultivo-fertilizante
  deleteRelacion(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/cultivo-fertilizante/delete/${id}`);
  }
}
