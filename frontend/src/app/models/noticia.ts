export interface Noticia {
    id: number;
    titulo: string;
    contenido: string;
    fechapublicacion: string; // LocalDate → string en el JSON
    userId: number;    
}
