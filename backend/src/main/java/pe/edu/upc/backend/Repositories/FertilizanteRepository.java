package pe.edu.upc.backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.backend.Entities.Fertilizante;

public interface FertilizanteRepository extends JpaRepository<Fertilizante, Long> {
}