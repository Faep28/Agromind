package pe.edu.upc.backend.Entitie;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "notificaciones") // Define el nombre de la tabla en la base de datos
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Estrategia de generación automática del identificador
    private Long identificador;

    private String encabezado; // Título de la notificación
    private String contenido; // Texto del mensaje
    private LocalDate fechaDeEnvio; // Fecha en que se envió
    private boolean leido; // Estado de lectura
    private String categoria; // Tipo o categoría de la notificación

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id") // Clave foránea que vincula con la entidad Usuario
    private User usuarioRelacionado; // Usuario destinatario de la notificación
}

