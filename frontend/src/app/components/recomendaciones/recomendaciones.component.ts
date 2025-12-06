import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Cultivo } from '../../models/cultivo';
import { Servicio } from '../../models/servicio';
import { SolicitudServicio } from '../../models/solicitud-servicio';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { CultivoService } from '../../services/cultivo-service';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { environment } from '../../../environments/environment';

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
    standalone: true,
    imports: [
        CommonModule,
        FormsModule,
        MatCardModule,
        MatFormFieldModule,
        MatSelectModule,
        MatButtonModule,
        MatIconModule,
        MatProgressBarModule,
        MatSnackBarModule
    ],
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
    
    private apiUrl = environment.apiUrl;
    isLoading: boolean = false;

    constructor(
        private http: HttpClient,
        private router: Router,
        private cultivoService: CultivoService,
        private snackBar: MatSnackBar
    ) { }

    ngOnInit(): void {
        this.cargarCultivos();
        this.cargarServiciosDisponibles(); // Cargar la lista de servicios para que el usuario pueda elegir
    }

    cargarCultivos(): void {
        this.isLoading = true;
        
        // Obtener el ID del usuario logueado
        const clienteId = Number(localStorage.getItem('user_id'));
        
        if (!clienteId) {
            this.mensajeError = 'Error: Usuario no autenticado';
            this.isLoading = false;
            return;
        }

        // Obtener cultivos del usuario actual
        this.cultivoService.getCultivosByCliente(clienteId).subscribe({
            next: (data) => {
                this.cultivos = data;
                this.isLoading = false;
                if (data.length === 0) {
                    this.mensajeError = 'No tienes cultivos registrados.';
                }
            },
            error: (err) => {
                this.mensajeError = 'Error al cargar la lista de cultivos.';
                console.error(err);
                this.isLoading = false;
            }
        });
    }

    cargarServiciosDisponibles(): void {
        this.http.get<Servicio[]>(`${this.apiUrl}/servicios/list`).subscribe({
            next: (data) => {
                this.serviciosDisponibles = data;
            },
            error: (err) => {
                console.error('Error al cargar servicios:', err);
                this.snackBar.open('Error al cargar servicios disponibles', 'Cerrar', { duration: 3000 });
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
        
        this.isLoading = true;
        const cultivoId = this.cultivoSeleccionadoId;
        const servicioId = this.servicioSeleccionadoId;
        
        const url = `${this.apiUrl}/solicitudes-servicios/servicio/${servicioId}/cultivo/${cultivoId}`;

        this.http.post<SolicitudServicioResponseDTO>(url, payload).subscribe({
            next: (response) => {
                this.tareasRecomendadas = response.tareasRecomendadas;
                this.isLoading = false;
                this.snackBar.open('Recomendaciones obtenidas exitosamente', 'Cerrar', { duration: 3000 });
            },
            error: (err) => {
                console.error('Error en la solicitud:', err);
                this.mensajeError = 'Error al obtener las tareas. Verifique que el Cultivo y Servicio existan.';
                this.tareasRecomendadas = '';
                this.isLoading = false;
                this.snackBar.open('Error al obtener recomendaciones', 'Cerrar', { duration: 3000 });
            }
        });
    }
}