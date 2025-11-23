package pe.edu.upc.backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.backend.Entities.Servicio;

public interface ServicioRepository extends JpaRepository<Servicio, Long> {
}
