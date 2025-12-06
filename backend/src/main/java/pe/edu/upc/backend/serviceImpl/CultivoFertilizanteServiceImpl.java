package pe.edu.upc.backend.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.backend.entities.Cultivo;
import pe.edu.upc.backend.entities.CultivoFertilizante;
import pe.edu.upc.backend.entities.Fertilizante;
import pe.edu.upc.backend.repositories.CultivoFertilizanteRepository;
import pe.edu.upc.backend.repositories.CultivoRepository;
import pe.edu.upc.backend.repositories.FertilizanteRepository;
import pe.edu.upc.backend.services.CultivoFertilizanteService;

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
        // Validar duplicados ANTES de crear
        List<CultivoFertilizante> existentes = cultivoFertilizanteRepository
                .findByCultivoIdAndFertilizanteId(cultivoId, fertilizanteId);

        if (!existentes.isEmpty()) {
            throw new IllegalStateException("Este fertilizante ya está asignado a este cultivo");
        }

        // Tu código actual continúa aquí (buscar cultivo, fertilizante, setear, guardar)
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

    @Override
    public List<CultivoFertilizante> findByCultivoId(Long cultivoId) {
        return cultivoFertilizanteRepository.findByCultivoId(cultivoId);
    }
}