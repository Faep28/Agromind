package pe.edu.upc.backend.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "servicios")
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //Identificador

    private String nombre; //nombre
    private String descripcion; //descripcion
    private String categoria;
    private Double precio;  // Cambio aquí: usando `double` para el precio
    private String estado;
    private String tareasRecomendadas;  // Texto de tareas recomendadas o adicionales

    // Relación OneToMany con SolicitudServicio
    @JsonIgnore
    @OneToMany(mappedBy = "servicio", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<SolicitudServicio> solicitudes;


}
