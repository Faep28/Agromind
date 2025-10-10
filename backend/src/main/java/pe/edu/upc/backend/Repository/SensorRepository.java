package pe.edu.upc.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.backend.Entitie.Sensor;

public interface SensorRepository extends JpaRepository<Sensor, Long> {
}
