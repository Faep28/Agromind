package pe.edu.upc.backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.backend.Entitie.Fertilizante;
import pe.edu.upc.backend.Service.FertilizanteService;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/fertilizantes")
public class FertilizanteController {

    @Autowired
    private FertilizanteService fertilizanteService;

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