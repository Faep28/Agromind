package pe.edu.upc.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "noticias") // Define el nombre de la tabla en la base de datos
public class Noticia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Estrategia de generación automática del identificador
    private Long id;

    private String titulo; // Título

    @Column(length = 2000)
    private String contenido; // Texto del contenido

    private LocalDate fechapublicacion; // Fecha en que se publico
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] imagen;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id") // foreign key que vincula con la entidad Usuario
    private User usuarioRelacionado; // Usuario destinatario de la noticia
}

