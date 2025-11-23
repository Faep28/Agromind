package pe.edu.upc.backend.services;

import pe.edu.upc.backend.entities.Parcela;

import java.util.List;

public interface ParcelaService {

    Parcela add(Parcela parcela);  // Crear nueva parcela
    List<Parcela> findAll();  // Obtener todas las parcelas
    Parcela edit(Parcela parcela);  // Actualizar parcela
    void deleteById(Long id);  // Eliminar parcela

    // JPQL Query 3
    List<Object[]> obtenerTotalParcelasYCultivosPorCliente(Long clienteId);
    List<Parcela> findByClienteId(Long clienteId);
}
