package pe.edu.upc.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.backend.Entitie.User;

public interface UserRepository extends JpaRepository<User,Long> {
}
