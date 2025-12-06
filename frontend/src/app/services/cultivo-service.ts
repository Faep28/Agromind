import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Cultivo } from '../models/cultivo';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CultivoService {

  private apiUrl = `${environment.apiUrl}/cultivos`;

  constructor(private http: HttpClient) {}

  listAll(): Observable<Cultivo[]> {
    return this.http.get<Cultivo[]>(`${this.apiUrl}/list`);
  }

  // Obtener cultivos por cliente (usuario logueado)
  getCultivosByCliente(clienteId: number): Observable<Cultivo[]> {
    return this.http.get<Cultivo[]>(`${this.apiUrl}/cliente/${clienteId}`);
  }

  // Obtener cultivos por parcela (nuevo endpoint en backend)
  getByParcela(parcelaId: number): Observable<Cultivo[]> {
    return this.http.get<Cultivo[]>(`${this.apiUrl}/parcela/${parcelaId}`);
  }

  // Crear un cultivo asociado a una parcela
  createForParcela(parcelaId: number, cultivo: Partial<Cultivo>): Observable<Cultivo> {
    return this.http.post<Cultivo>(`${this.apiUrl}/insert/${parcelaId}`, cultivo);
  }

  update(id: number, cultivo: Partial<Cultivo>): Observable<Cultivo> {
    return this.http.put<Cultivo>(`${this.apiUrl}/update/${id}`, cultivo);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/delete/${id}`);
  }

  getById(id: number): Observable<Cultivo> {
    return this.http.get<Cultivo>(`${this.apiUrl}/${id}`);
  }

  estadisticasPorParcela(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/estadisticas/por-parcela`);
  }

  buscarPorNombreYEstado(nombre: string, estado: string): Observable<Cultivo[]> {
    return this.http.get<Cultivo[]>(`${this.apiUrl}/buscar?nombre=${encodeURIComponent(nombre)}&estado=${encodeURIComponent(estado)}`);
  }

  buscarPorTemporada(temporada: string): Observable<Cultivo[]> {
    return this.http.get<Cultivo[]>(`${this.apiUrl}/temporada/${encodeURIComponent(temporada)}`);
  }

  estadisticasPorEstado(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/estadisticas/estado`);
  }
}
