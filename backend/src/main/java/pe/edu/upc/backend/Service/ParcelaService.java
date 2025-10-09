package pe.edu.upc.backend.Service;

import pe.edu.upc.backend.Entitie.Parcela;

import java.util.List;

public interface ParcelaService {

    Parcela add(Parcela parcela);  // Crear nueva parcela
    List<Parcela> findAll();  // Obtener todas las parcelas
    Parcela edit(Parcela parcela);  // Actualizar parcela
    void deleteById(Long id);  // Eliminar parcela
}
