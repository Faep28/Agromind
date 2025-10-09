package pe.edu.upc.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.backend.Entitie.Cultivo;

public interface CultivoRepository extends JpaRepository<Cultivo, Long> {
}
