package pe.edu.upc.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.backend.Entitie.Notificacion;

import java.util.List;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    List<Notificacion> findByUserId(Long userId);
}
