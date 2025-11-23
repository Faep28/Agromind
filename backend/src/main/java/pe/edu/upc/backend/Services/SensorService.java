package pe.edu.upc.backend.Services;

import pe.edu.upc.backend.Entities.Sensor;

import java.util.List;

public interface SensorService {
    Sensor add(Sensor sensor);
    List<Sensor> findAll();
    Sensor edit(Long id, Sensor sensor);
    void deleteById(Long id);
    List<Object[]> countSensoresActivosPorTipo();
    List<Sensor> findByEstadoIgnoreCase(String estado);

}
