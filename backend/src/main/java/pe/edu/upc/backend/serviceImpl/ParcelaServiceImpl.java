package pe.edu.upc.backend.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.backend.entities.Parcela;
import pe.edu.upc.backend.repositories.ClienteRepository;
import pe.edu.upc.backend.repositories.ParcelaRepository;
import pe.edu.upc.backend.services.ParcelaService;

import java.util.List;

@Service
public class ParcelaServiceImpl implements ParcelaService {

    @Autowired
    private ParcelaRepository parcelaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    //----------------------------------CRUD----------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------
    @Override
    public Parcela add(Parcela parcela) {
        // Simplemente guardamos la parcela, ya que el cliente ya ha sido asociado en el controlador
        return parcelaRepository.save(parcela);
    }

    @Override
    public List<Parcela> findAll() {
        return parcelaRepository.findAll();  // Obtener todas las parcelas
    }

    @Override
    public Parcela edit(Parcela parcela) {
        // Verifica si la parcela existe antes de actualizar
        if (parcelaRepository.existsById(parcela.getId())) {
            return parcelaRepository.save(parcela);  // Si existe, la actualiza
        }
        return null;  // Si no existe, retorna null
    }

    @Override
    public void deleteById(Long id) {
        if (parcelaRepository.existsById(id)) {
            parcelaRepository.deleteById(id);  // Eliminar la parcela
        }
    }
    //----------------------------------CRUD----------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------

    //----------------------------------JPQL QUERY 3----------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------
    @Override
    public List<Object[]> obtenerTotalParcelasYCultivosPorCliente(Long clienteId) {
        return parcelaRepository.obtenerTotalParcelasYCultivosPorCliente(clienteId);
    }
    //----------------------------------------------------------------------------------------------------------------------
    @Override
    public List<Parcela> findByClienteId(Long clienteId) {
        return parcelaRepository.findParcelasByClienteId(clienteId);
    }
}
