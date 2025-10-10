package pe.edu.upc.backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.backend.Entitie.Servicio;
import pe.edu.upc.backend.Service.ServicioService;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/servicios")  //http://localhost:8080/api/servicios
public class ServicioController {

    @Autowired
    private ServicioService servicioService;

    // Crear un nuevo servicio
    @PostMapping("/insert")  //http://localhost:8080/api/servicios/insert
    public ResponseEntity<Servicio> add(@RequestBody Servicio servicio) {
        Servicio createdServicio = servicioService.add(servicio);
        return new ResponseEntity<>(createdServicio, HttpStatus.CREATED);
    }

    // Obtener todos los servicios
    @GetMapping("/list")   //http://localhost:8080/api/servicios/list
    public List<Servicio> getAll() {
        return servicioService.findAll();
    }
    // Actualizar un servicio
    @PutMapping("/update/{id}")
    public ResponseEntity<Servicio> update(@PathVariable Long id, @RequestBody Servicio servicioDetails) {
        Servicio updatedServicio = servicioService.edit(id, servicioDetails);
        return new ResponseEntity<>(updatedServicio, HttpStatus.OK);
    }

    // Eliminar un servicio
    @DeleteMapping("/delete/{id}")  //http://localhost:8080/api/servicios/delete/{id}
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        servicioService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // 204 No Content indica que la eliminación fue exitosa
    }

}
