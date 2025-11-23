package pe.edu.upc.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.backend.entities.SolicitudServicio;

public interface SolicitudServicioRepository extends JpaRepository<SolicitudServicio, Long> {
}
