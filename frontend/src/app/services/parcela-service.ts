import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Parcela } from '../models/parcela';

@Injectable({
  providedIn: 'root'
})
export class ParcelaService {

  private apiUrl = "http://localhost:8080/api/parcelas";

  constructor(private http: HttpClient) {}

  // Obtener parcelas por cliente
  getByClienteId(clienteId: number): Observable<Parcela[]> {
    return this.http.get<Parcela[]>(`${this.apiUrl}/cliente/${clienteId}`);
  }

  // Obtener todas (por si acaso)
  getAll(): Observable<Parcela[]> {
    return this.http.get<Parcela[]>(`${this.apiUrl}/list`);
  }

  // Crear parcela
  create(clienteId: number, parcela: Parcela): Observable<Parcela> {
    return this.http.post<Parcela>(`${this.apiUrl}/insert/${clienteId}`, parcela);
  }

  // Actualizar parcela
  update(id: number, parcela: Parcela): Observable<Parcela> {
    return this.http.put<Parcela>(`${this.apiUrl}/update/${id}`, parcela);
  }

  // Eliminar parcela
  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/delete/${id}`);
  }
}
