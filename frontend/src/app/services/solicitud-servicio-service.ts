import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SolicitudServicio } from '../models/solicitud-servicio';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class SolicitudServicioService {
  private base = `${environment.apiUrl}/api/solicitudes-servicios`;

  constructor(private http: HttpClient) {}

  listAll(): Observable<SolicitudServicio[]> {
    return this.http.get<SolicitudServicio[]>(`${this.base}/list`);
  }

  listMy(): Observable<SolicitudServicio[]> {
    return this.http.get<SolicitudServicio[]>(`${this.base}/my`);
  }

  createForServicioAndCultivo(servicioId: number, cultivoId: number, payload: Partial<SolicitudServicio>) {
    return this.http.post(`${this.base}/insert/${servicioId}/${cultivoId}`, payload);
  }
}
