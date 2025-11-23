package pe.edu.upc.backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.backend.Entities.Authority;

public interface AuthorityRepository extends JpaRepository<Authority,Long> {
}
