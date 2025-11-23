package pe.edu.upc.backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.backend.Entities.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente,Long> {
}
