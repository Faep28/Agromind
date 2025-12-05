export interface CultivoFertilizante {

     id: number;
    cultivoId: number;        // relación con Cultivo
    fertilizanteId: number;   // relación con Fertilizante
    fechaAplicacion: string;  // LocalDate → string en el JSON
    cantidad: number;
}
