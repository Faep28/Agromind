package pe.edu.upc.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SolicitudServicioResponseDTO {

    private Long solicitudId;
    private LocalDate fechaSolicitud;
    private String estado;
    private String cultivoNombre;
    private String servicioNombre;
    private String tareasRecomendadas; // El campo que realmente le interesa al frontend
}