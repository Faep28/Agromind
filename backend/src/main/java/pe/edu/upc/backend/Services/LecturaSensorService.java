package pe.edu.upc.backend.Services;

import pe.edu.upc.backend.Entities.LecturaSensor;

import java.util.List;

public interface LecturaSensorService {
    LecturaSensor add(Long sensorId, Long parcelaId, LecturaSensor lecturaSensor);
    List<LecturaSensor> findAll();
    LecturaSensor edit(Long id, LecturaSensor lecturaSensor);
    void deleteById(Long id);
}
