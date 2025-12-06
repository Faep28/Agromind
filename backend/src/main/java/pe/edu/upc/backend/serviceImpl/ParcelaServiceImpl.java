package pe.edu.upc.backend.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.backend.entities.Parcela;
import pe.edu.upc.backend.exceptions.ResourceNotFoundException;
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
        if (!parcelaRepository.existsById(parcela.getId())) {
            throw new ResourceNotFoundException("Parcela id: " + parcela.getId() + " not found");
        }
        return parcelaRepository.save(parcela);
    }

    @Override
    public void deleteById(Long id) {
        if (!parcelaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Parcela id: " + id + " not found");
        }
        parcelaRepository.deleteById(id);
    }
    //----------------------------------CRUD----------------------------------------------------

    //----------------------------------JPQL QUERY 3----------------------------------------------------
    @Override
    public List<Object[]> obtenerTotalParcelasYCultivosPorCliente(Long clienteId) {
        return parcelaRepository.obtenerTotalParcelasYCultivosPorCliente(clienteId);
    }

    //----------------------------------------------------------------------------------------------------------------------
    @Override
    public List<Parcela> findByClienteId(Long clienteId) {
        return parcelaRepository.findParcelasByClienteId(clienteId);
    }

    @Override
    public Parcela findById(Long id) {
        return parcelaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parcela id: " + id + " not found"));
    }
}
