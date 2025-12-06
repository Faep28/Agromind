package pe.edu.upc.backend.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.backend.entities.LecturaSensor;
import pe.edu.upc.backend.entities.Parcela;
import pe.edu.upc.backend.entities.Sensor;
import pe.edu.upc.backend.exceptions.ResourceNotFoundException;
import pe.edu.upc.backend.repositories.LecturaSensorRepository;
import pe.edu.upc.backend.repositories.ParcelaRepository;
import pe.edu.upc.backend.repositories.SensorRepository;
import pe.edu.upc.backend.services.LecturaSensorService;

import java.util.List;

@Service
public class LecturaSensorServiceImpl implements LecturaSensorService {

    @Autowired
    private LecturaSensorRepository lecturaSensorRepository;

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private ParcelaRepository parcelaRepository;


    @Override
    public LecturaSensor add(Long sensorId, Long parcelaId, LecturaSensor lecturaSensor) {
        Sensor sensor = sensorRepository.findById(sensorId)
                .orElseThrow(() -> new ResourceNotFoundException("Sensor id: " + sensorId + " not found"));

        Parcela parcela = parcelaRepository.findById(parcelaId)
                .orElseThrow(() -> new ResourceNotFoundException("Parcela id: " + parcelaId + " not found"));

        lecturaSensor.setSensor(sensor);
        lecturaSensor.setParcela(parcela);
        return lecturaSensorRepository.save(lecturaSensor);
    }

    @Override
    public List<LecturaSensor> findAll() {
        return lecturaSensorRepository.findAll();
    }

    @Override
    public LecturaSensor edit(Long id, LecturaSensor lecturaSensor) {
        if (!lecturaSensorRepository.existsById(id)) {
            throw new ResourceNotFoundException("LecturaSensor id: " + id + " not found");
        }
        lecturaSensor.setId(id);
        return lecturaSensorRepository.save(lecturaSensor);
    }

    @Override
    public void deleteById(Long id) {
        if (!lecturaSensorRepository.existsById(id)) {
            throw new ResourceNotFoundException("LecturaSensor id: " + id + " not found");
        }
        lecturaSensorRepository.deleteById(id);
    }
}
