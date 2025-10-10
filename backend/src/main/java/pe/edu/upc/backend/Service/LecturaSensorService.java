package pe.edu.upc.backend.Service;

import pe.edu.upc.backend.Entitie.LecturaSensor;

import java.util.List;

public interface LecturaSensorService {
    LecturaSensor add(Long sensorId, Long parcelaId, LecturaSensor lecturaSensor);
    List<LecturaSensor> findAll();
    LecturaSensor edit(Long id, LecturaSensor lecturaSensor);
    void deleteById(Long id);
}
