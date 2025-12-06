package pe.edu.upc.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.backend.dtos.SolicitudServicioResponseDTO;
import pe.edu.upc.backend.entities.SolicitudServicio;
import pe.edu.upc.backend.services.SolicitudServicioService;

import java.util.List;

//@CrossOrigin("*")
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

    @GetMapping("/cultivo/{cultivoId}")  //http://localhost:8080/api/solicitudes-servicios/cultivo/1
    public ResponseEntity<List<SolicitudServicio>> getByCultivoId(@PathVariable Long cultivoId) {
        List<SolicitudServicio> solicitudes = solicitudServicioService.findByCultivoId(cultivoId);

        if (solicitudes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(solicitudes, HttpStatus.OK);
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

    // POST /api/solicitudes-servicios/servicio/{servicioId}/cultivo/{cultivoId}
    @PostMapping("/servicio/{servicioId}/cultivo/{cultivoId}")
    public ResponseEntity<SolicitudServicioResponseDTO> createSolicitud(
            @PathVariable Long servicioId,
            @PathVariable Long cultivoId,
            @RequestBody SolicitudServicio solicitudDetails) {
        try {
            // 1. Crear la Solicitud, que devuelve la entidad con el Servicio asociado
            SolicitudServicio newSolicitud = solicitudServicioService.add(servicioId, cultivoId, solicitudDetails);

            // 2. Construir el DTO de respuesta con la información clave
            SolicitudServicioResponseDTO responseDTO = new SolicitudServicioResponseDTO(
                    newSolicitud.getId(),
                    newSolicitud.getFechaSolicitud(),
                    newSolicitud.getEstado(),
                    newSolicitud.getCultivo().getNombre(),
                    newSolicitud.getServicio().getNombre(),
                    newSolicitud.getServicio().getTareasRecomendadas() // Tareas recomendadas!
            );

            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);

        } catch (RuntimeException e) {
            // Error si el Servicio o Cultivo no se encuentran
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
