package pe.edu.upc.backend.Services;

import pe.edu.upc.backend.Entities.Fertilizante;

import java.util.List;

public interface FertilizanteService {
    Fertilizante add(Fertilizante fertilizante);
    List<Fertilizante> findAll();
    Fertilizante edit(Long id, Fertilizante fertilizante);
    void deleteById(Long id);
}