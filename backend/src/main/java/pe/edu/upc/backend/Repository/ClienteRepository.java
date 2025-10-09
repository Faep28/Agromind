package pe.edu.upc.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.backend.Entitie.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente,Long> {
}
