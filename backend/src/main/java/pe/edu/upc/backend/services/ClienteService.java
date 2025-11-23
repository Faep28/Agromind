package pe.edu.upc.backend.services;

import pe.edu.upc.backend.entities.Cliente;

import java.util.List;

public interface ClienteService {
    //CRUD
    public Cliente add(Cliente cliente);
    public List<Cliente> findAll();
    public Cliente edit(Cliente cliente);
    void deleteById(Long id);
}
