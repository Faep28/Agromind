package pe.edu.upc.backend.Services;

import pe.edu.upc.backend.Entities.Cultivo;

import java.util.List;

public interface CultivoService {
    Cultivo add(Cultivo cultivo);
    List<Cultivo> findAll();
    Cultivo edit(Cultivo cultivo);
    void deleteById(Long id);

    List<Object[]> countCultivosPorParcela();

    List<Cultivo> findByNombreContainingIgnoreCaseAndEstadoIgnoreCase(String nombre, String estado);

    //Listar cultivos por temporada
    List<Cultivo> findByTemporadaIgnoreCase(String temporada);


    Long countByEstadoIgnoreCase(String estado);
}
