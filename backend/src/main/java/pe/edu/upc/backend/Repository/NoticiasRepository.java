package pe.edu.upc.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.backend.Entitie.Noticia;

public interface NoticiasRepository extends JpaRepository<Noticia,Long> {
}
