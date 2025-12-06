import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Servicio } from '../models/servicio';

@Injectable({
  providedIn: 'root'
})
export class ServicioService {
  private base = 'http://localhost:8080/api/servicios';

  constructor(private http: HttpClient) {}

  listAll(): Observable<Servicio[]> {
    // Siguiendo el mismo patrón que CultivoService
    return this.http.get<Servicio[]>(`${this.base}/list`);
  }

  getById(id: number) {
    return this.http.get<Servicio>(`${this.base}/${id}`);
  }
}
