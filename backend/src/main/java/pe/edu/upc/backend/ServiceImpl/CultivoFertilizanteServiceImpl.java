package pe.edu.upc.backend.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.backend.Entities.Cultivo;
import pe.edu.upc.backend.Entities.CultivoFertilizante;
import pe.edu.upc.backend.Entities.Fertilizante;
import pe.edu.upc.backend.Repositories.CultivoFertilizanteRepository;
import pe.edu.upc.backend.Repositories.CultivoRepository;
import pe.edu.upc.backend.Repositories.FertilizanteRepository;
import pe.edu.upc.backend.Services.CultivoFertilizanteService;

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

    @Override
    public List<String> findFertilizantesByCultivoId(Long cultivoId) {
        return cultivoFertilizanteRepository.findFertilizantesByCultivoId(cultivoId);
    }

    @Override
    public List<Object[]> findTop5FertilizantesMasUsados() {
        return cultivoFertilizanteRepository.findTop5FertilizantesMasUsados();

    }
}