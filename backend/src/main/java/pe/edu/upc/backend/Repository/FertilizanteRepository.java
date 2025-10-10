package pe.edu.upc.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.backend.Entitie.Fertilizante;

public interface FertilizanteRepository extends JpaRepository<Fertilizante, Long> {
}