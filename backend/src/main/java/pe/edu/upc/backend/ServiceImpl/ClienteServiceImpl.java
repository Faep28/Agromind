package pe.edu.upc.backend.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.backend.Entities.Cliente;
import pe.edu.upc.backend.Entities.User;
import pe.edu.upc.backend.Repositories.ClienteRepository;
import pe.edu.upc.backend.Repositories.UserRepository;
import pe.edu.upc.backend.Services.ClienteService;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private UserRepository userRepository;
    //----------------------------------CRUD----------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------

    @Override
    public Cliente add(Cliente cliente) {
        // Intentar obtener el usuario con el ID del cliente
        User user = userRepository.findById(cliente.getUser().getId()).orElse(null);

        if (user == null) {
            throw new RuntimeException("User not found!");
        }

        // Verifica si el usuario fue encontrado correctamente
        System.out.println("User found: " + user);  // Log de depuración

        cliente.setUser(user);  // Asociar el usuario al cliente
        return clienteRepository.save(cliente);  // Guardar el cliente
    }
    @Override
    public List<Cliente> findAll() {
        return clienteRepository.findAll();  // Obtener todos los clientes
    }
    @Override
    public Cliente edit(Cliente cliente) {
        if (clienteRepository.existsById(cliente.getId())) {
            return clienteRepository.save(cliente);  // Si existe, actualiza el cliente
        }
        return null;  // Si no existe, devuelve null
    }
    @Override
    public void deleteById(Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);  // Eliminar el cliente por ID
        }
    }
    //----------------------------------CRUD----------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------

}
