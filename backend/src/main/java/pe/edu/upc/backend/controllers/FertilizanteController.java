package pe.edu.upc.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.backend.entities.Fertilizante;
import pe.edu.upc.backend.services.FertilizanteService;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/fertilizantes")
public class FertilizanteController {

    @Autowired
    private FertilizanteService fertilizanteService;


// === FertilizanteController ===
// POST: http://localhost:8080/api/fertilizantes/insert
//     → Crea un nuevo fertilizante.
// GET:  http://localhost:8080/api/fertilizantes/list
//     → Lista todos los fertilizantes registrados.
// PUT:  http://localhost:8080/api/fertilizantes/update/{id}
//     → Actualiza un fertilizante existente.
// DELETE: http://localhost:8080/api/fertilizantes/delete/{id}
//     → Elimina un fertilizante por ID.



    // Crear un nuevo fertilizante
    @PostMapping("/insert")
    public ResponseEntity<Fertilizante> add(@RequestBody Fertilizante fertilizante) {
        Fertilizante createdFertilizante = fertilizanteService.add(fertilizante);
        return new ResponseEntity<>(createdFertilizante, HttpStatus.CREATED);
    }

    // Obtener todos los fertilizantes
    @GetMapping("/list")
    public List<Fertilizante> getAll() {
        return fertilizanteService.findAll();
    }

    // Actualizar un fertilizante
    @PutMapping("/update/{id}")
    public ResponseEntity<Fertilizante> update(@PathVariable Long id, @RequestBody Fertilizante fertilizante) {
        Fertilizante updatedFertilizante = fertilizanteService.edit(id, fertilizante);
        return new ResponseEntity<>(updatedFertilizante, HttpStatus.OK);
    }

    // Eliminar un fertilizante
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        fertilizanteService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}