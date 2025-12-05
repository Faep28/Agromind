package pe.edu.upc.backend.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.backend.entities.Cultivo;
import pe.edu.upc.backend.repositories.CultivoRepository;
import pe.edu.upc.backend.repositories.ParcelaRepository;
import pe.edu.upc.backend.services.CultivoService;

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
        return cultivoRepository.save(cultivo);
    }

    @Override
    public void deleteById(Long id) {
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
        return cultivoRepository.findById(id).orElse(null);
    }

}
