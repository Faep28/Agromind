package pe.edu.upc.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.backend.entities.CultivoFertilizante;
import pe.edu.upc.backend.services.CultivoFertilizanteService;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/cultivo-fertilizante")
public class CultivoFertilizanteController {

    @Autowired
    private CultivoFertilizanteService cultivoFertilizanteService;


// === CultivoFertilizanteController ===
// POST: http://localhost:8080/api/cultivo-fertilizante/insert/{cultivoId}/{fertilizanteId}
//     → Crea una relación entre un cultivo y un fertilizante.
// GET:  http://localhost:8080/api/cultivo-fertilizante/list
//     → Lista todas las relaciones cultivo-fertilizante.
// PUT:  http://localhost:8080/api/cultivo-fertilizante/update/{id}
//     → Actualiza una relación existente.
// DELETE: http://localhost:8080/api/cultivo-fertilizante/delete/{id}
//     → Elimina una relación por ID.



    // Crear una nueva relación cultivo-fertilizante
    //http://localhost:8080/api/cultivo-fertilizante/insert/{cultivoId}/{fertilizanteId}
    @PostMapping("/insert/{cultivoId}/{fertilizanteId}")
    public ResponseEntity<CultivoFertilizante> add(
            @PathVariable Long cultivoId,
            @PathVariable Long fertilizanteId,
            @RequestBody CultivoFertilizante cultivoFertilizante) {

        CultivoFertilizante created = cultivoFertilizanteService.add(cultivoId, fertilizanteId, cultivoFertilizante);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // Obtener todas las relaciones
    @GetMapping("/list")//http://localhost:8080/api/cultivo-fertilizante/list
    public List<CultivoFertilizante> getAll() {
        return cultivoFertilizanteService.findAll();
    }

    // Actualizar una relación
    @PutMapping("/update/{id}")
    public ResponseEntity<CultivoFertilizante> update(
            @PathVariable Long id,
            @RequestBody CultivoFertilizante cultivoFertilizante) {

        CultivoFertilizante updated = cultivoFertilizanteService.edit(id, cultivoFertilizante);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    // Eliminar una relación
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cultivoFertilizanteService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/fertilizantes/cultivo/{cultivoId}")
    public ResponseEntity<List<String>> getFertilizantesByCultivo(@PathVariable Long cultivoId) {
        List<String> fertilizantes = cultivoFertilizanteService.findFertilizantesByCultivoId(cultivoId);
        return new ResponseEntity<>(fertilizantes, HttpStatus.OK);
    }
    //TOP 5 FERTILIZANTES
    //http://localhost:8080/api/cultivo-fertilizante/top-fertilizantes
    @GetMapping("/top-fertilizantes")
    public ResponseEntity<List<Object[]>> getTopFertilizantes() {
        List<Object[]> data = cultivoFertilizanteService.findTop5FertilizantesMasUsados();
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}