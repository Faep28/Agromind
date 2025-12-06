package pe.edu.upc.backend.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.backend.entities.Cliente;
import pe.edu.upc.backend.entities.User;
import pe.edu.upc.backend.exceptions.ResourceNotFoundException;
import pe.edu.upc.backend.repositories.ClienteRepository;
import pe.edu.upc.backend.repositories.UserRepository;
import pe.edu.upc.backend.services.ClienteService;

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
        User user = userRepository.findById(cliente.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User id: " + cliente.getUser().getId() + " not found"));

        cliente.setUser(user);
        return clienteRepository.save(cliente);
    }

    @Override
    public List<Cliente> findAll() {
        return clienteRepository.findAll();  // Obtener todos los clientes
    }
    @Override
    public Cliente edit(Cliente cliente) {
        if (!clienteRepository.existsById(cliente.getId())) {
            throw new ResourceNotFoundException("Cliente id: " + cliente.getId() + " not found");
        }
        return clienteRepository.save(cliente);
    }

    @Override
    public void deleteById(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente id: " + id + " not found");
        }
        clienteRepository.deleteById(id);
    }
    //----------------------------------CRUD----------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------

}
