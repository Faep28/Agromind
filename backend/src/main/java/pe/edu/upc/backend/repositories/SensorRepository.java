package pe.edu.upc.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.edu.upc.backend.entities.Sensor;

import java.util.List;

public interface SensorRepository extends JpaRepository<Sensor, Long> {

    @Query(value = "SELECT tipo, COUNT(*) AS total_activos " +
            "FROM sensores " +
            "WHERE LOWER(estado) = 'activo' " +
            "GROUP BY tipo", nativeQuery = true)
    List<Object[]> countSensoresActivosPorTipo();


    //Query method para listar todos los sensores activos
    List<Sensor> findByEstadoIgnoreCase(String estado);

}
