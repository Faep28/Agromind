import { Component, OnInit } from '@angular/core';
import { Cultivo } from '../../models/cultivo'; // Asegúrate de que la ruta sea correcta
import { Servicio } from '../../models/servicio'; // Asegúrate de que la ruta sea correcta
import { SolicitudServicio } from '../../models/solicitud-servicio'; // Modelo para el payload
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { environment } from '../../../environments/environment'; // Para la URL base del API

// DTO de respuesta simplificado del backend
interface SolicitudServicioResponseDTO {
    solicitudId: number;
    fechaSolicitud: string;
    estado: string;
    cultivoNombre: string;
    servicioNombre: string;
    tareasRecomendadas: string; // Contiene el texto de las recomendaciones
}

@Component({
    selector: 'app-recomendaciones',
    standalone: false,
    templateUrl: './recomendaciones.component.html',
    styleUrls: ['./recomendaciones.component.css']
})
export class RecomendacionesComponent implements OnInit {

    // --- Datos para la UI ---
    cultivos: Cultivo[] = [];
    serviciosDisponibles: Servicio[] = [];
    
    // --- Selección del Usuario ---
    cultivoSeleccionadoId: number | null = null;
    servicioSeleccionadoId: number | null = null; 

    // --- Resultados ---
    tareasRecomendadas: string = '';
    mensajeError: string = '';
    
    // Nota: Debes reemplazar esta URL base con la de tu entorno (ej. http://localhost:8080)
    private apiUrl = environment.apiUrl; 

    constructor(
        private http: HttpClient,
        private router: Router // Útil para navegar si es necesario
    ) { }

    ngOnInit(): void {
        this.cargarCultivos();
        this.cargarServiciosDisponibles(); // Cargar la lista de servicios para que el usuario pueda elegir
    }

    cargarCultivos(): void {
        // GET /api/recomendaciones/cultivos
        this.http.get<Cultivo[]>(`${this.apiUrl}/api/recomendaciones/cultivos`).subscribe({
            next: (data) => {
                this.cultivos = data;
            },
            error: (err) => {
                this.mensajeError = 'Error al cargar la lista de cultivos.';
                console.error(err);
            }
        });
    }

    cargarServiciosDisponibles(): void {
        // GET /api/servicios
        this.http.get<Servicio[]>(`${this.apiUrl}/api/servicios`).subscribe({
            next: (data) => {
                this.serviciosDisponibles = data;
                // Opcional: Si solo hay un servicio clave, seleccionarlo por defecto.
                if (data.length > 0 && !this.servicioSeleccionadoId) {
                    // Puedes establecer un ID por defecto si el servicio de "Recomendaciones" es fijo (ej. ID 1)
                    // this.servicioSeleccionadoId = 1; 
                }
            },
            error: (err) => {
                console.error('Error al cargar servicios:', err);
            }
        });
    }

    obtenerRecomendaciones(): void {
        if (!this.cultivoSeleccionadoId || !this.servicioSeleccionadoId) {
            this.mensajeError = 'Debe seleccionar un Cultivo y un Servicio.';
            this.tareasRecomendadas = '';
            return;
        }

        // Limpiar errores
        this.mensajeError = '';
        
        // Crear el payload de la solicitud (modelo SolicitudServicio)
        const today = new Date().toISOString().split('T')[0]; // Formato YYYY-MM-DD
        const payload: Partial<SolicitudServicio> = { 
            fechaSolicitud: today,
            estado: 'Recomendación', // Usamos un estado para indicar que es una consulta de recomendación
        };
        
        const cultivoId = this.cultivoSeleccionadoId;
        const servicioId = this.servicioSeleccionadoId;
        
        // POST /api/solicitudes-servicios/servicio/{servicioId}/cultivo/{cultivoId}
        const url = `${this.apiUrl}/api/solicitudes-servicios/servicio/${servicioId}/cultivo/${cultivoId}`;

        this.http.post<SolicitudServicioResponseDTO>(url, payload).subscribe({
            next: (response) => {
                this.tareasRecomendadas = response.tareasRecomendadas;
                // Puedes agregar lógica para mostrar los tips diarios si el backend los devuelve también.
            },
            error: (err) => {
                console.error('Error en la solicitud:', err);
                this.mensajeError = 'Error al obtener las tareas. Verifique que el Cultivo y Servicio existan.';
                this.tareasRecomendadas = '';
            }
        });
    }
}