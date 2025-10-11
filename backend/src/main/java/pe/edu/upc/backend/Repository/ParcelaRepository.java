package pe.edu.upc.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.upc.backend.Entitie.Parcela;

import java.util.List;

public interface ParcelaRepository extends JpaRepository<Parcela, Long> {

    // Query 3: Obtener cantidad total de parcelas y cultivos activos por cliente
    @Query("SELECT c.id, c.nombre, " +
            "COUNT(DISTINCT p.id) AS totalParcelas, " +
            "COUNT(DISTINCT cu.id) AS totalCultivosActivos " +
            "FROM Cliente c " +
            "LEFT JOIN Parcela p ON p.cliente.id = c.id " +
            "LEFT JOIN Cultivo cu ON cu.parcela.id = p.id AND cu.estado = 'Activo' " +
            "WHERE c.id = :clienteId " +
            "GROUP BY c.id, c.nombre")
    List<Object[]> obtenerTotalParcelasYCultivosPorCliente(@Param("clienteId") Long clienteId);
    //Query 1: Listar todas las parcelas de un cliente específico
    @Query("SELECT p FROM Parcela p WHERE p.cliente.id = :clienteId")
    List<Parcela> findParcelasByClienteId(@Param("clienteId") Long clienteId);
}