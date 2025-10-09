package pe.edu.upc.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.backend.Entitie.Parcela;

public interface ParcelaRepository extends JpaRepository<Parcela, Long> {
}
