package pe.edu.upc.backend.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.backend.entities.Cultivo;
import pe.edu.upc.backend.entities.Parcela;
import pe.edu.upc.backend.exceptions.ResourceNotFoundException;
import pe.edu.upc.backend.repositories.CultivoRepository;
import pe.edu.upc.backend.repositories.ParcelaRepository;
import pe.edu.upc.backend.services.CultivoService;

import java.util.ArrayList;
import java.util.List;

@Service
public class CultivoServiceImpl implements CultivoService {

    @Autowired
    private CultivoRepository cultivoRepository;
    @Autowired
    private ParcelaRepository parcelaRepository;
    //----------------------------------CRUD----------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------

    @Override
    public Cultivo add(Cultivo cultivo) {

        return cultivoRepository.save(cultivo);
    }

    @Override
    public List<Cultivo> findAll() {
        return cultivoRepository.findAll();
    }

    @Override
    public Cultivo edit(Cultivo cultivo) {
        if (!cultivoRepository.existsById(cultivo.getId())) {
            throw new ResourceNotFoundException("Cultivo id: " + cultivo.getId() + " not found");
        }
        return cultivoRepository.save(cultivo);
    }


    @Override
    public void deleteById(Long id) {
        if (!cultivoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cultivo id: " + id + " not found");
        }
        cultivoRepository.deleteById(id);
    }
    //----------------------------------CRUD----------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------
    @Override
    public List<Object[]> countCultivosPorParcela() {
        return cultivoRepository.countCultivosPorParcela();
    }

    @Override
    public List<Cultivo> findByNombreContainingIgnoreCaseAndEstadoIgnoreCase(String nombre, String estado) {
        return cultivoRepository.findByNombreContainingIgnoreCaseAndEstadoIgnoreCase(nombre, estado);
    }

    @Override
    public List<Cultivo> findByTemporadaIgnoreCase(String temporada) {
        return cultivoRepository.findByTemporadaIgnoreCase(temporada);
    }

    @Override
    public Long countByEstadoIgnoreCase(String estado) {
        return cultivoRepository.countByEstadoIgnoreCase(estado);
    }

    @Override
    public List<Cultivo> findByParcelaId(Long parcelaId) {
        return cultivoRepository.findByParcelaId(parcelaId); // Llama al método del repositorio
    }

    @Override
    public Cultivo findById(Long id) {
        return cultivoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cultivo id: " + id + " not found"));
    }

    public List<Cultivo> getCultivosByCliente(Long clienteId) {
        // Obtener todas las parcelas del cliente
        List<Parcela> parcelas = parcelaRepository.findParcelasByClienteId(clienteId);

        // Obtener todos los cultivos de esas parcelas
        List<Cultivo> cultivos = new ArrayList<>();
        for (Parcela parcela : parcelas) {
            cultivos.addAll(cultivoRepository.findByParcelaId(parcela.getId()));
        }

        return cultivos;
    }

}
