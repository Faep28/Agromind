package pe.edu.upc.backend.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.backend.Entitie.Cultivo;
import pe.edu.upc.backend.Entitie.CultivoFertilizante;
import pe.edu.upc.backend.Entitie.Fertilizante;
import pe.edu.upc.backend.Repository.CultivoFertilizanteRepository;
import pe.edu.upc.backend.Repository.CultivoRepository;
import pe.edu.upc.backend.Repository.FertilizanteRepository;
import pe.edu.upc.backend.Service.CultivoFertilizanteService;

import java.util.List;

@Service
public class CultivoFertilizanteServiceImpl implements CultivoFertilizanteService {

    @Autowired
    private CultivoFertilizanteRepository cultivoFertilizanteRepository;

    @Autowired
    private CultivoRepository cultivoRepository;

    @Autowired
    private FertilizanteRepository fertilizanteRepository;

    @Override
    public CultivoFertilizante add(Long cultivoId, Long fertilizanteId, CultivoFertilizante cultivoFertilizante) {
        Cultivo cultivo = cultivoRepository.findById(cultivoId)
                .orElseThrow(() -> new RuntimeException("Cultivo no encontrado"));
        Fertilizante fertilizante = fertilizanteRepository.findById(fertilizanteId)
                .orElseThrow(() -> new RuntimeException("Fertilizante no encontrado"));

        cultivoFertilizante.setCultivo(cultivo);
        cultivoFertilizante.setFertilizante(fertilizante);

        return cultivoFertilizanteRepository.save(cultivoFertilizante);
    }

    @Override
    public List<CultivoFertilizante> findAll() {
        return cultivoFertilizanteRepository.findAll();
    }

    @Override
    public CultivoFertilizante edit(Long id, CultivoFertilizante cultivoFertilizante) {
        if (!cultivoFertilizanteRepository.existsById(id)) {
            throw new RuntimeException("Relación Cultivo-Fertilizante no encontrada");
        }
        cultivoFertilizante.setId(id);
        return cultivoFertilizanteRepository.save(cultivoFertilizante);
    }

    @Override
    public void deleteById(Long id) {
        if (cultivoFertilizanteRepository.existsById(id)) {
            cultivoFertilizanteRepository.deleteById(id);
        } else {
            System.out.println("Relación Cultivo-Fertilizante no encontrada");
        }
    }
}