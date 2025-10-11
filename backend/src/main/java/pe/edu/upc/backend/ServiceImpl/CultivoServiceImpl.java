package pe.edu.upc.backend.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.backend.Entitie.Cultivo;
import pe.edu.upc.backend.Entitie.Parcela;
import pe.edu.upc.backend.Repository.CultivoRepository;
import pe.edu.upc.backend.Repository.ParcelaRepository;
import pe.edu.upc.backend.Service.CultivoService;

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
    public List<Cultivo> findByNombreContainingIgnoreCaseAndEstado(String nombre, String estado) {
        return cultivoRepository.findByNombreContainingIgnoreCaseAndEstado(nombre, estado);
    }


}
