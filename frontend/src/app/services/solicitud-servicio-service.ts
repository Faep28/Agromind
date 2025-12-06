import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SolicitudServicio } from '../models/solicitud-servicio';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class SolicitudServicioService {
  // environment.apiUrl already contiene el prefijo '/api'
  private base = `${environment.apiUrl}/solicitudes-servicios`;

  constructor(private http: HttpClient) {}

  listAll(): Observable<SolicitudServicio[]> {
    return this.http.get<SolicitudServicio[]>(`${this.base}/list`);
  }


  createForServicioAndCultivo(servicioId: number, cultivoId: number, payload: Partial<SolicitudServicio>) {
    return this.http.post(`${this.base}/insert/${servicioId}/${cultivoId}`, payload);
  }

  // Listar solicitudes por cultivo
  listByCultivo(cultivoId: number): Observable<SolicitudServicio[]> {
  return this.http.get<SolicitudServicio[]>(`${this.base}/cultivo/${cultivoId}`);
}

}
