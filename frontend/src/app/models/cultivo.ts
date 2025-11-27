export interface Cultivo {
    id: number;
  nombre: string;
  descripcion: string;
  temporada: string;
  fechaSiembra: string;           
  fechaCosechaEsperada: string;   
  estado: string;
  parcelaId: number;
}
