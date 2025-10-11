package pe.edu.upc.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.upc.backend.Entitie.CultivoFertilizante;

import java.util.List;

public interface CultivoFertilizanteRepository extends JpaRepository<CultivoFertilizante, Long> {
    @Query("SELECT f.nombre FROM CultivoFertilizante cf JOIN cf.fertilizante f WHERE cf.cultivo.id = :cultivoId")
    List<String> findFertilizantesByCultivoId(@Param("cultivoId") Long cultivoId);

    @Query(value = "SELECT f.nombre, COUNT(cf.id) AS total_aplicaciones " +
            "FROM cultivo_fertilizante cf " +
            "JOIN fertilizantes f ON cf.fertilizante_id = f.id " +
            "GROUP BY f.nombre " +
            "ORDER BY total_aplicaciones DESC " +
            "LIMIT 5", nativeQuery = true)
    List<Object[]> findTop5FertilizantesMasUsados();

}