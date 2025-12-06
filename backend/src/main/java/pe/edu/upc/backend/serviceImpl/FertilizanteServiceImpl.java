package pe.edu.upc.backend.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.backend.entities.Fertilizante;
import pe.edu.upc.backend.exceptions.ResourceNotFoundException;
import pe.edu.upc.backend.repositories.FertilizanteRepository;
import pe.edu.upc.backend.services.FertilizanteService;

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
            throw new ResourceNotFoundException("Fertilizante id: " + id + " not found");
        }
        fertilizante.setId(id);
        return fertilizanteRepository.save(fertilizante);
    }

    @Override
    public void deleteById(Long id) {
        if (!fertilizanteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Fertilizante id: " + id + " not found");
        }
        fertilizanteRepository.deleteById(id);
    }
}