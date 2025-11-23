package pe.edu.upc.backend.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.backend.Entities.Cliente;
import pe.edu.upc.backend.Entities.User;
import pe.edu.upc.backend.Repositories.UserRepository;
import pe.edu.upc.backend.Services.ClienteService;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/clientes") //http://localhost:8080/api/clientes
public class ClienteController {

    @Autowired
    private ClienteService clienteService;
    @Autowired
    private UserRepository userRepository;

    // --------------------------CRUD----------------------------------------------
    //---------------------------------------------------------------------------------------------------------------
    // PARA CREAR UN CLIENTE DEBE CREARSE PRIMERO UN USER... Y LUEGO VINCULAS LAS ID

    @PostMapping("/insert/{userId}") //http://localhost:8080/api/clientes/insert/{userId}
    public ResponseEntity<Cliente> add(@PathVariable Long userId, @RequestBody Cliente cliente) {

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        cliente.setUser(user);
        Cliente createdCliente = clienteService.add(cliente);
        return new ResponseEntity<>(createdCliente, HttpStatus.CREATED);
    }

    // Obtener todos los clientes
    @GetMapping("/list")   //http://localhost:8080/api/clientes/list
    public List<Cliente> getAllClientes() {
        return clienteService.findAll();
    }
    // Actualizar un cliente
    @PutMapping("/update/{id}") //http://localhost:8080/api/clientes/update/{id}
    public ResponseEntity<Cliente> updateCliente(@PathVariable Long id, @RequestBody Cliente clienteDetails) {
        clienteDetails.setId(id);  // Establecemos el ID del cliente desde la URL
        Cliente updatedCliente = clienteService.edit(clienteDetails);
        if (updatedCliente != null) {
            return new ResponseEntity<>(updatedCliente, HttpStatus.OK);  // Si la actualización es exitosa
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Si el cliente no fue encontrado
        }
    }
    // Eliminar un cliente
    @DeleteMapping("/delete/{id}")  //http://localhost:8080/api/clientes/delete/{id}
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        clienteService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // 204 No Content indica que la eliminación fue exitosa
    }
    // --------------------------CRUD----------------------------------------------
    //---------------------------------------------------------------------------------------------------------------


}
