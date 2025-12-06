package pe.edu.upc.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.backend.entities.Cliente;
import pe.edu.upc.backend.entities.Parcela;
import pe.edu.upc.backend.repositories.ClienteRepository;
import pe.edu.upc.backend.services.ParcelaService;

import java.util.List;

//@CrossOrigin("*")
@RestController
@RequestMapping("/api/parcelas")  //http://localhost:8080/api/parcelas
public class ParcelaController {

    @Autowired
    private ParcelaService parcelaService;

    @Autowired
    private ClienteRepository clienteRepository;

    // --------------------------CRUD----------------------------------------------
    //---------------------------------------------------------------------------------------------------------------
    // Crear una nueva parcela asociada a un cliente
    @PostMapping("/insert/{clienteId}")  //http://localhost:8080/api/parcelas/insert/{clienteId}
    public ResponseEntity<Parcela> add(@PathVariable Long clienteId, @RequestBody Parcela parcela) {

        Cliente cliente = clienteRepository.findById(clienteId).orElse(null);
        if (cliente == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  // Si no existe el cliente, retorno 400
        }

        parcela.setCliente(cliente);

        Parcela createdParcela = parcelaService.add(parcela);

        return new ResponseEntity<>(createdParcela, HttpStatus.CREATED);
    }

    // Obtener todas las parcelas
    @GetMapping("/list") //http://localhost:8080/api/parcelas/list
    public List<Parcela> getAllParcelas() {
        return parcelaService.findAll();  // Llamamos al servicio para obtener todas las parcelas
    }

    //Actualizar parcela
    @PutMapping("/update/{id}")
    public ResponseEntity<Parcela> updateParcela(
            @PathVariable Long id,
            @RequestBody Parcela parcelaDetails
    ) {
        Parcela existing = parcelaService.findById(id);

        if (existing == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        existing.setNombre(parcelaDetails.getNombre());
        existing.setLongitud(parcelaDetails.getLongitud());
        existing.setLatitud(parcelaDetails.getLatitud());
        existing.setTamano(parcelaDetails.getTamano());
        // cliente NO se toca, se mantiene

        Parcela updated = parcelaService.edit(existing);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }



    // Eliminar una parcela
    @DeleteMapping("/delete/{id}") //http://localhost:8080/api/parcelas/delete/{id}
    public ResponseEntity<Void> deleteParcela(@PathVariable Long id) {
        parcelaService.deleteById(id);  // Llamamos al servicio para eliminar la parcela
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // Retorna 204 No Content
    }
    // --------------------------CRUD----------------------------------------------
    //---------------------------------------------------------------------------------------------------------------

    //----------------------------------JPQL QUERY 3----------------------------------------------------
    // GET: http://localhost:8080/api/parcelas/estadisticas/cliente/{clienteId}
    @GetMapping("/estadisticas/cliente/{clienteId}")
    public ResponseEntity<List<Object[]>> getEstadisticasPorCliente(@PathVariable Long clienteId) {
        List<Object[]> estadisticas = parcelaService.obtenerTotalParcelasYCultivosPorCliente(clienteId);
        return new ResponseEntity<>(estadisticas, HttpStatus.OK);
    }
    //----------------------------------------------------------------------------------------------------------------------
    @GetMapping("/cliente/{clienteId}")  //http://localhost:8080/api/parcelas/cliente/{clienteId}
    public ResponseEntity<List<Parcela>> getParcelasByCliente(@PathVariable Long clienteId) {
        List<Parcela> parcelas = parcelaService.findByClienteId(clienteId);
        return new ResponseEntity<>(parcelas, HttpStatus.OK);
    }

}