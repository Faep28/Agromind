package pe.edu.upc.backend.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.backend.Entitie.LecturaSensor;
import pe.edu.upc.backend.Entitie.Parcela;
import pe.edu.upc.backend.Entitie.Sensor;
import pe.edu.upc.backend.Repository.LecturaSensorRepository;
import pe.edu.upc.backend.Repository.ParcelaRepository;
import pe.edu.upc.backend.Repository.SensorRepository;
import pe.edu.upc.backend.Service.LecturaSensorService;

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
        Sensor sensor = sensorRepository.findById(sensorId).orElseThrow(() -> new RuntimeException("Sensor no encontrado"));
        Parcela parcela = parcelaRepository.findById(parcelaId).orElseThrow(() -> new RuntimeException("Parcela no encontrada"));

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
        if (!lecturaSensorRepository.existsById(id)){
            throw new RuntimeException("Lectura de sensor no encontrada");
        }
        lecturaSensor.setId(id);
        return lecturaSensorRepository.save(lecturaSensor);
    }

    @Override
    public void deleteById(Long id) {
        if (lecturaSensorRepository.existsById(id)){
            lecturaSensorRepository.deleteById(id);
        } else {
            System.out.println("Lectura de sensor no encontrada");
        }
    }
}
