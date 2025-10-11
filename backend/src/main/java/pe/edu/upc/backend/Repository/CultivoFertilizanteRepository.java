package pe.edu.upc.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.upc.backend.Entitie.CultivoFertilizante;

import java.util.List;

public interface CultivoFertilizanteRepository extends JpaRepository<CultivoFertilizante, Long> {
    @Query("SELECT f.nombre FROM CultivoFertilizante cf JOIN cf.fertilizante f WHERE cf.cultivo.id = :cultivoId")
    List<String> findFertilizantesByCultivoId(@Param("cultivoId") Long cultivoId);


}