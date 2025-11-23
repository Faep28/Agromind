package pe.edu.upc.backend.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.backend.Entities.Fertilizante;
import pe.edu.upc.backend.Repositories.FertilizanteRepository;
import pe.edu.upc.backend.Services.FertilizanteService;

import java.util.List;

@Service
public class FertilizanteServiceImpl implements FertilizanteService {

    @Autowired
    private FertilizanteRepository fertilizanteRepository;

    @Override
    public Fertilizante add(Fertilizante fertilizante) {
        return fertilizanteRepository.save(fertilizante);
    }

    @Override
    public List<Fertilizante> findAll() {
        return fertilizanteRepository.findAll();
    }

    @Override
    public Fertilizante edit(Long id, Fertilizante fertilizante) {
        if (!fertilizanteRepository.existsById(id)) {
            throw new RuntimeException("Fertilizante no encontrado");
        }
        fertilizante.setId(id);
        return fertilizanteRepository.save(fertilizante);
    }

    @Override
    public void deleteById(Long id) {
        if (fertilizanteRepository.existsById(id)) {
            fertilizanteRepository.deleteById(id);
        } else {
            System.out.println("Fertilizante no encontrado");
        }
    }
}