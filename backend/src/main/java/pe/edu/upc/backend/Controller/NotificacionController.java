package pe.edu.upc.backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.backend.Entitie.Notificacion;
import pe.edu.upc.backend.Service.NotificacionService;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/notificaciones") // http://localhost:8080/api/notificaciones
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    // Crear nueva notificación
    @PostMapping("/insert")
    public ResponseEntity<Notificacion> add(@RequestBody Notificacion notificacion) {
        Notificacion created = notificacionService.add(notificacion);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // Listar todas las notificaciones
    @GetMapping("/list")
    public List<Notificacion> listAll() {
        return notificacionService.findAll();
    }

    // Actualizar notificación
    @PutMapping("/update/{id}")
    public ResponseEntity<Notificacion> update(@PathVariable Long id, @RequestBody Notificacion notificacionDetails) {
        notificacionDetails.setId(id);
        Notificacion updated = notificacionService.edit(notificacionDetails);
        if (updated != null) {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar notificación
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        notificacionService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Buscar notificaciones por usuario
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notificacion>> findByUserId(@PathVariable Long userId) {
        List<Notificacion> notificaciones = notificacionService.findByUserId(userId);
        return new ResponseEntity<>(notificaciones, HttpStatus.OK);
    }
}
