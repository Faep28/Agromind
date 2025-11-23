package pe.edu.upc.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.backend.entities.LecturaSensor;

public interface LecturaSensorRepository extends JpaRepository<LecturaSensor, Long> {
}
