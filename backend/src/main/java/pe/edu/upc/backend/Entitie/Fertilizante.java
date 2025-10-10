package pe.edu.upc.backend.Entitie;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "fertilizantes")
public class Fertilizante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 50)
    private String tipo;

    @Column(name = "dosis_recomendada", precision = 10, scale = 2)
    private BigDecimal dosisRecomendada;

    @JsonIgnore
    @OneToMany(mappedBy = "fertilizante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CultivoFertilizante> cultivoFertilizantes;
}