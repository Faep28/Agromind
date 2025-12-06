import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CultivoFertilizante } from '../models/cultivo-fertilizante';
import { Fertilizante } from '../models/fertilizante';

@Injectable({
  providedIn: 'root'
})
export class FertilizanteService {
  private base = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  // Obtener todas las relaciones cultivo-fertilizante de un cultivo específico
  getFertilizantesByCultivo(cultivoId: number): Observable<CultivoFertilizante[]> {
    return this.http.get<CultivoFertilizante[]>(`${this.base}/cultivo-fertilizante/cultivo/${cultivoId}`);
  }

  // Listar todos los fertilizantes disponibles
  listAll(): Observable<Fertilizante[]> {
    return this.http.get<Fertilizante[]>(`${this.base}/fertilizantes/list`);
  }

  // Crear un nuevo fertilizante
  create(fertilizante: Partial<Fertilizante>): Observable<Fertilizante> {
    return this.http.post<Fertilizante>(`${this.base}/fertilizantes/insert`, fertilizante);
  }

  // Actualizar un fertilizante
  update(id: number, fertilizante: Partial<Fertilizante>): Observable<Fertilizante> {
    return this.http.put<Fertilizante>(`${this.base}/fertilizantes/update/${id}`, fertilizante);
  }

  // Eliminar un fertilizante
  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/fertilizantes/delete/${id}`);
  }

  // Asignar fertilizante a un cultivo (crear relación cultivo-fertilizante)
  asignarFertilizanteACultivo(cultivoId: number, fertilizanteId: number, data: { fechaAplicacion: string, cantidad: number }): Observable<CultivoFertilizante> {
    return this.http.post<CultivoFertilizante>(`${this.base}/cultivo-fertilizante/insert/${cultivoId}/${fertilizanteId}`, data);
  }

  // Actualizar relación cultivo-fertilizante
  updateRelacion(id: number, data: { fechaAplicacion: string, cantidad: number }): Observable<CultivoFertilizante> {
    return this.http.put<CultivoFertilizante>(`${this.base}/cultivo-fertilizante/update/${id}`, data);
  }

  // Eliminar relación cultivo-fertilizante
  deleteRelacion(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/cultivo-fertilizante/delete/${id}`);
  }
}
