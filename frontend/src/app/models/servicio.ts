export interface Servicio {

     id: number;
    nombre: string;
    descripcion: string;
    categoria: string;
    precio: number;  // Usamos `number` para el precio
    estado: string;
    tareasRecomendadas: string;
}
