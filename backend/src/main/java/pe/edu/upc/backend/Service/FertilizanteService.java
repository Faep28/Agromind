package pe.edu.upc.backend.Service;

import pe.edu.upc.backend.Entitie.Fertilizante;

import java.util.List;

public interface FertilizanteService {
    Fertilizante add(Fertilizante fertilizante);
    List<Fertilizante> findAll();
    Fertilizante edit(Long id, Fertilizante fertilizante);
    void deleteById(Long id);
}