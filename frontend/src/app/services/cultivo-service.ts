import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Cultivo } from '../models/cultivo';

@Injectable({
  providedIn: 'root'
})
export class CultivoService {
  private base = 'http://localhost:8080/api/cultivos';

  constructor(private http: HttpClient) {}

  listAll(): Observable<Cultivo[]> {
    return this.http.get<Cultivo[]>(`${this.base}/list`);
  }

  // Obtener cultivos por parcela (nuevo endpoint en backend)
  getByParcela(parcelaId: number): Observable<Cultivo[]> {
    return this.http.get<Cultivo[]>(`${this.base}/parcela/${parcelaId}`);
  }

  // Crear un cultivo asociado a una parcela
  createForParcela(parcelaId: number, cultivo: Partial<Cultivo>): Observable<Cultivo> {
    return this.http.post<Cultivo>(`${this.base}/insert/${parcelaId}`, cultivo);
  }

  update(id: number, cultivo: Partial<Cultivo>): Observable<Cultivo> {
    return this.http.put<Cultivo>(`${this.base}/update/${id}`, cultivo);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/delete/${id}`);
  }

  getById(id: number): Observable<Cultivo> {
    return this.http.get<Cultivo>(`${this.base}/${id}`);
  }

  estadisticasPorParcela(): Observable<any[]> {
    return this.http.get<any[]>(`${this.base}/estadisticas/por-parcela`);
  }

  buscarPorNombreYEstado(nombre: string, estado: string): Observable<Cultivo[]> {
    return this.http.get<Cultivo[]>(`${this.base}/buscar?nombre=${encodeURIComponent(nombre)}&estado=${encodeURIComponent(estado)}`);
  }

  buscarPorTemporada(temporada: string): Observable<Cultivo[]> {
    return this.http.get<Cultivo[]>(`${this.base}/temporada/${encodeURIComponent(temporada)}`);
  }

  estadisticasPorEstado(): Observable<any> {
    return this.http.get<any>(`${this.base}/estadisticas/estado`);
  }
}
