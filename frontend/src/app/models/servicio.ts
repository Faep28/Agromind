export interface Servicio {

     id: number;
    nombre: string;
    descripcion: string;
    categoria: string;
    precio: number;  // Usamos `number` para el precio
    estado: string;
    tareasRecomendadas: string;
    imagen?: string; // base64 opcional o ruta si decides usarla
}
