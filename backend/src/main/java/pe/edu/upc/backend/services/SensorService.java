package pe.edu.upc.backend.services;

import pe.edu.upc.backend.entities.Sensor;

import java.util.List;

public interface SensorService {
    Sensor add(Sensor sensor);
    List<Sensor> findAll();
    Sensor edit(Long id, Sensor sensor);
    void deleteById(Long id);
    List<Object[]> countSensoresActivosPorTipo();
    List<Sensor> findByEstadoIgnoreCase(String estado);

}
