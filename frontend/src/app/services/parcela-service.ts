import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Parcela } from '../models/parcela';
import { UserService } from './user-service';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ParcelaService {

  private apiUrl = `${environment.apiUrl}/parcelas`;

  constructor(private http: HttpClient, private userService: UserService) {}

  // Obtener parcelas por cliente
  getByClienteId(clienteId: number): Observable<Parcela[]> {
    return this.http.get<Parcela[]>(`${this.apiUrl}/cliente/${clienteId}`);
  }

  // Obtener todas
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
