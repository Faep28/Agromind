package pe.edu.upc.backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.backend.Entitie.Sensor;
import pe.edu.upc.backend.Service.SensorService;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/sensores")  //http://localhost:8080/api/sensores
public class SensorController {

    @Autowired
    private SensorService sensorService;

    @PostMapping("/insert")  //http://localhost:8080/api/sensores/insert
    public ResponseEntity<Sensor> add(@RequestBody Sensor sensor) {
        Sensor createdSensor = sensorService.add(sensor);
        return new ResponseEntity<>(createdSensor, HttpStatus.CREATED);
    }

    @GetMapping("/list")  //http://localhost:8080/api/sensores/list
    public List<Sensor> getAll() {
        return  sensorService.findAll();
    }

    @PutMapping("/update/{id}")  //http://localhost:8080/api/sensores/update/{id}
    public ResponseEntity<Sensor> update(@PathVariable Long id, @RequestBody Sensor sensor) {
        Sensor updatedSensor = sensorService.edit(id, sensor);
        return new ResponseEntity<>(updatedSensor, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")  //http://localhost:8080/api/sensores/delete/{id}
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        sensorService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content indica que la eliminación se realizó correctamente
    }
    @GetMapping("/activos/por-tipo")
    public ResponseEntity<List<Object[]>> getSensoresActivosPorTipo() {
        List<Object[]> data = sensorService.countSensoresActivosPorTipo();
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    // Listar sensores según su estado (activo / inactivo)
    @GetMapping("/estado/{estado}")     //http://localhost:8080/api/sensores/estado/inactivo
    public List<Sensor> findByEstado(@PathVariable String estado) {
        return sensorService.findByEstadoIgnoreCase(estado);
    }
}
