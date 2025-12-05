package pe.edu.upc.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.edu.upc.backend.entities.Cultivo;

import java.util.List;

public interface CultivoRepository extends JpaRepository<Cultivo, Long> {

    @Query("SELECT p.nombre, COUNT(c.id) FROM Cultivo c JOIN c.parcela p GROUP BY p.nombre")
    List<Object[]> countCultivosPorParcela();

    //Query method de buscar cultivos por nombre y estado
    List<Cultivo> findByNombreContainingIgnoreCaseAndEstadoIgnoreCase(String nombre, String estado);

    //Query method de listar todos los cultivos registrados en una temporada específica
    List<Cultivo> findByTemporadaIgnoreCase(String temporada);

    //Query method para contar cuántos cultivos están activos vs inactivos
    Long countByEstadoIgnoreCase(String estado);

    List<Cultivo> findByParcelaId(Long parcelaId);

}
