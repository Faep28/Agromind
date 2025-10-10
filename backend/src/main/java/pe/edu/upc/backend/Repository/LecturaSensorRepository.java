package pe.edu.upc.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.backend.Entitie.LecturaSensor;

public interface LecturaSensorRepository extends JpaRepository<LecturaSensor, Long> {
}
