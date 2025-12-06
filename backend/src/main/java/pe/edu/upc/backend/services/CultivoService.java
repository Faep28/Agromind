package pe.edu.upc.backend.services;

import pe.edu.upc.backend.entities.Cultivo;

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


    List<Cultivo> findByParcelaId(Long parcelaId);
    Long countByEstadoIgnoreCase(String estado);
    Cultivo findById(Long id);
    List<Cultivo> getCultivosByCliente(Long clienteId);
}
