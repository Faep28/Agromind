package pe.edu.upc.backend.Service;

import pe.edu.upc.backend.Entitie.Cultivo;

import java.util.List;

public interface CultivoService {
    Cultivo add(Cultivo cultivo);
    List<Cultivo> findAll();
    Cultivo edit(Cultivo cultivo);
    void deleteById(Long id);

    List<Object[]> countCultivosPorParcela();

}
