package pe.edu.upc.backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.backend.Entitie.Cultivo;
import pe.edu.upc.backend.Entitie.Parcela;
import pe.edu.upc.backend.Repository.ParcelaRepository;
import pe.edu.upc.backend.Service.CultivoService;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/cultivos")
public class CultivoController {
    @Autowired
    private CultivoService cultivoService;

    @Autowired
    private ParcelaRepository parcelaRepository;
    // --------------------------CRUD----------------------------------------------
    //---------------------------------------------------------------------------------------------------------------
    // Crear un nuevo cultivo asociado a una parcela
    @PostMapping("/insert/{parcelaId}")
    public ResponseEntity<Cultivo> add(@PathVariable Long parcelaId, @RequestBody Cultivo cultivo) {
        // Verificar que la parcela asociada existe en la base de datos
        Parcela parcela = parcelaRepository.findById(parcelaId).orElse(null);
        if (parcela == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  // Si la parcela no existe, retornamos error 400
        }

        // Asociamos la parcela al cultivo
        cultivo.setParcela(parcela);

        // Guardamos el cultivo
        Cultivo createdCultivo = cultivoService.add(cultivo);

        // Retornamos el cultivo creado
        return new ResponseEntity<>(createdCultivo, HttpStatus.CREATED);
    }

    // Obtener todos los cultivos
    @GetMapping("/list")
    public List<Cultivo> getAllCultivos() {
        return cultivoService.findAll();
    }
    // Actualizar un cultivo
    @PutMapping("/update/{id}")
    public ResponseEntity<Cultivo> updateCultivo(@PathVariable Long id, @RequestBody Cultivo cultivoDetails) {
        cultivoDetails.setId(id);
        Cultivo updatedCultivo = cultivoService.edit(cultivoDetails);
        if (updatedCultivo != null) {
            return new ResponseEntity<>(updatedCultivo, HttpStatus.OK);  // Si la actualización es exitosa
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Si el cultivo no fue encontrado
        }
    }
    // Eliminar un cultivo
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCultivo(@PathVariable Long id) {
        cultivoService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // 204 No Content indica que la eliminación fue exitosa
    }
    // --------------------------CRUD----------------------------------------------
    //---------------------------------------------------------------------------------------------------------------


}
