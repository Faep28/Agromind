package pe.edu.upc.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.backend.entities.Servicio;

public interface ServicioRepository extends JpaRepository<Servicio, Long> {
}
