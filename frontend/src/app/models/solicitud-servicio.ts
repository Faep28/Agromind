export interface SolicitudServicio {
    id: number;
    fechaSolicitud: string;  // LocalDate → string en el JSON
    estado: string;
    servicioId: number;     // Relación con Servicio (solo el ID)
    cultivoId: number; 
}
