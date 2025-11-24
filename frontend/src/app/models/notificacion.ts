export interface Notificacion {
    id: number;
  titulo: string;
  mensaje: string;
  fechaEnvio: string;   // LocalDateTime llega como string en el JSON
  esLeido: boolean;
  tipo: string;
  userId: number;
}
