package pe.edu.upc.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.backend.Entitie.Authority;

public interface AuthorityRepository extends JpaRepository<Authority,Long> {
}
