package pe.edu.upc.backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.backend.Entitie.SolicitudServicio;
import pe.edu.upc.backend.Service.SolicitudServicioService;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/solicitudes-servicios")  //http://localhost:8080/api/solicitudes-servicios
public class SolicitudServicioController {
    @Autowired
    private SolicitudServicioService solicitudServicioService;

    // Crear una nueva solicitud de servicio
    @PostMapping("/insert/{servicioId}/{cultivoId}")  //http://localhost:8080/api/solicitudes-servicios/insert/{servicioId}/{cultivoId}
    public ResponseEntity<SolicitudServicio> add(
            @PathVariable Long servicioId,
            @PathVariable Long cultivoId,
            @RequestBody SolicitudServicio solicitudServicio) {

        // Crear la solicitud de servicio y asociarla con el servicio y el cultivo
        SolicitudServicio createdSolicitud = solicitudServicioService.add(servicioId, cultivoId, solicitudServicio);
        return new ResponseEntity<>(createdSolicitud, HttpStatus.CREATED);
    }

    // Obtener todas las solicitudes de servicio
    @GetMapping("/list")  //http://localhost:8080/api/solicitudes-servicios/list
    public List<SolicitudServicio> getAll() {
        return solicitudServicioService.findAll();
    }
    // Editar una solicitud de servicio
    @PutMapping("/update/{id}")  //http://localhost:8080/api/solicitudes-servicios/update/{id}
    public ResponseEntity<SolicitudServicio> update(
            @PathVariable Long id,
            @RequestBody SolicitudServicio solicitudServicio) {

        SolicitudServicio updatedSolicitud = solicitudServicioService.edit(id, solicitudServicio);
        return new ResponseEntity<>(updatedSolicitud, HttpStatus.OK);
    }
    // Eliminar una solicitud de servicio
    @DeleteMapping("/delete/{id}")  //http://localhost:8080/api/solicitudes-servicios/delete/{id}
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        solicitudServicioService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // 204 No Content indica que la eliminación fue exitosa
    }
}
