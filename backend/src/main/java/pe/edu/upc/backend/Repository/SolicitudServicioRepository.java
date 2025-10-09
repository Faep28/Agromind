package pe.edu.upc.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.backend.Entitie.SolicitudServicio;

public interface SolicitudServicioRepository extends JpaRepository<SolicitudServicio, Long> {
}
