package pe.edu.upc.backend.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.backend.entities.Sensor;
import pe.edu.upc.backend.exceptions.ResourceNotFoundException;
import pe.edu.upc.backend.repositories.SensorRepository;
import pe.edu.upc.backend.services.SensorService;

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
            throw new ResourceNotFoundException("Sensor id: " + id + " not found");
        }
        sensor.setId(id);
        return sensorRepository.save(sensor);
    }

    @Override
    public void deleteById(Long id) {
        if (!sensorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Sensor id: " + id + " not found");
        }
        sensorRepository.deleteById(id);
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
