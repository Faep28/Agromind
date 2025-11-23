package pe.edu.upc.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.backend.entities.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente,Long> {
}
