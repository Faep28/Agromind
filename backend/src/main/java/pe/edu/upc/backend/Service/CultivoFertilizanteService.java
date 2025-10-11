package pe.edu.upc.backend.Service;

import pe.edu.upc.backend.Entitie.CultivoFertilizante;

import java.util.List;

public interface CultivoFertilizanteService {
    CultivoFertilizante add(Long cultivoId, Long fertilizanteId, CultivoFertilizante cultivoFertilizante);
    List<CultivoFertilizante> findAll();
    CultivoFertilizante edit(Long id, CultivoFertilizante cultivoFertilizante);
    void deleteById(Long id);
    List<String> findFertilizantesByCultivoId(Long cultivoId);

}