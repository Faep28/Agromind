package pe.edu.upc.backend.Service;

import pe.edu.upc.backend.Entitie.Sensor;

import java.util.List;

public interface SensorService {
    Sensor add(Sensor sensor);
    List<Sensor> findAll();
    Sensor edit(Long id, Sensor sensor);
    void deleteById(Long id);
    List<Object[]> countSensoresActivosPorTipo();
}
