import { Cultivo } from "./cultivo";
import { Fertilizante } from "./fertilizante";

export interface CultivoFertilizante {
    id: number;
    cultivo: Cultivo;           // objeto Cultivo completo
    fertilizante: Fertilizante; // objeto Fertilizante completo
    fechaAplicacion: string;    // LocalDate → string en el JSON
    cantidad: number;
}
