package pe.edu.upc.backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.backend.Entities.LecturaSensor;

public interface LecturaSensorRepository extends JpaRepository<LecturaSensor, Long> {
}
