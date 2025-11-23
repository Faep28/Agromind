package pe.edu.upc.backend.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.backend.Entities.LecturaSensor;
import pe.edu.upc.backend.Services.LecturaSensorService;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/lecturas-sensores")   //http://localhost:8080/api/lecturas-sensores
public class LecturaSensorController {

    @Autowired
    private LecturaSensorService lecturaSensorService;

    @PostMapping("/insert/{sensorId}/{parcelaId}") //http://localhost:8080/api/lecturas-sensores/insert/{sensorId}/{parcelaId}
    public ResponseEntity<LecturaSensor> add(
            @PathVariable Long sensorId,
            @PathVariable Long parcelaId,
            @RequestBody LecturaSensor lecturaSensor){

        LecturaSensor createdLectura = lecturaSensorService.add(sensorId, parcelaId, lecturaSensor);
        return new ResponseEntity<>(createdLectura, HttpStatus.CREATED);
    }

    @GetMapping("/list")  //http://localhost:8080/api/lecturas-sensores/list
    public List<LecturaSensor> list(){
        return lecturaSensorService.findAll();
    }

    @PutMapping("/update/{id}")  //http://localhost:8080/api/lecturas-sensores/update/{id}
    public ResponseEntity<LecturaSensor> update(
            @PathVariable Long id,
            @RequestBody LecturaSensor lecturaSensor){
        LecturaSensor updatedLectura = lecturaSensorService.edit(id, lecturaSensor);
        return new ResponseEntity<>(updatedLectura, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")   //http://localhost:8080/api/lecturas-sensores/delete/{id}
    public ResponseEntity<LecturaSensor> delete(@PathVariable Long id){
        lecturaSensorService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content indica que la eliminación fue exitosa
    }
}
