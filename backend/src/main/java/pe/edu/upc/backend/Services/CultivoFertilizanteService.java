package pe.edu.upc.backend.Services;

import pe.edu.upc.backend.Entities.CultivoFertilizante;

import java.util.List;

public interface CultivoFertilizanteService {
    CultivoFertilizante add(Long cultivoId, Long fertilizanteId, CultivoFertilizante cultivoFertilizante);
    List<CultivoFertilizante> findAll();
    CultivoFertilizante edit(Long id, CultivoFertilizante cultivoFertilizante);
    void deleteById(Long id);
    List<String> findFertilizantesByCultivoId(Long cultivoId);

    List<Object[]> findTop5FertilizantesMasUsados();
}