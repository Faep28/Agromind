package pe.edu.upc.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.edu.upc.backend.Entitie.Cultivo;

import java.util.List;

public interface CultivoRepository extends JpaRepository<Cultivo, Long> {

    @Query("SELECT p.nombre, COUNT(c.id) FROM Cultivo c JOIN c.parcela p GROUP BY p.nombre")
    List<Object[]> countCultivosPorParcela();
}
