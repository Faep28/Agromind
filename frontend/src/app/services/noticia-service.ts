import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { Noticia } from '../models/noticia';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class NoticiaService {

  private apiUrl = `${environment.apiUrl}/noticias`;
  
  recurso:string ="create";

  // Subject para notificar cambios en noticias
  private noticiasActualizadas$ = new Subject<void>();

  constructor(private http:HttpClient){}

  new(formData:FormData){
    return this.http.post<Noticia>(this.apiUrl+"/"+this.recurso, formData);
  }

  // Método para obtener todas las noticias
  getAll(): Observable<Noticia[]> {
    return this.http.get<Noticia[]>(`${this.apiUrl}/list`);
  }

  // Método para eliminar una noticia
  delete(id: number): Observable<void> {
    // Hacemos una solicitud DELETE al backend usando el ID de la noticia
    return this.http.delete<void>(`${this.apiUrl}/delete/${id}`);
  }

  // Método para emitir cambios en noticias
  notificarCambios() {
    this.noticiasActualizadas$.next();
  }

  // Método para obtener el observable de cambios
  obtenerActualizaciones$() {
    return this.noticiasActualizadas$.asObservable();
  }
  
}


