package pe.edu.upc.backend.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.backend.Entitie.Sensor;
import pe.edu.upc.backend.Repository.SensorRepository;
import pe.edu.upc.backend.Service.SensorService;

import java.util.List;

@Service
public class SensorServiceImpl implements SensorService {
    @Autowired
    private SensorRepository sensorRepository;

    @Override
    public Sensor add(Sensor sensor) {
        return sensorRepository.save(sensor);
    }

    @Override
    public List<Sensor> findAll() {
        return sensorRepository.findAll();
    }

    @Override
    public Sensor edit(Long id, Sensor sensor) {
        if (!sensorRepository.existsById(id)) {
            throw new RuntimeException("Sensor no encontrado");
        }
        sensor.setId(id);
        return sensorRepository.save(sensor);
    }

    @Override
    public void deleteById(Long id) {
        if (sensorRepository.existsById(id)) {
            sensorRepository.deleteById(id);
        } else {
            System.out.println("Sensor no encontrado");
        }
    }
    @Override
    public List<Object[]> countSensoresActivosPorTipo() {
        return sensorRepository.countSensoresActivosPorTipo();
    }

    @Override
    public List<Sensor> findByEstadoIgnoreCase(String estado) {
        return sensorRepository.findByEstadoIgnoreCase(estado);
    }


}
