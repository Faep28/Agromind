package pe.edu.upc.backend.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.backend.entities.Cultivo;
import pe.edu.upc.backend.entities.CultivoFertilizante;
import pe.edu.upc.backend.entities.Fertilizante;
import pe.edu.upc.backend.exceptions.KeyRepeatedDataException;
import pe.edu.upc.backend.exceptions.ResourceNotFoundException;
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
        List<CultivoFertilizante> existentes = cultivoFertilizanteRepository
                .findByCultivoIdAndFertilizanteId(cultivoId, fertilizanteId);

        if (!existentes.isEmpty()) {
            throw new KeyRepeatedDataException("Fertilizante id: " + fertilizanteId + " ya está asignado al cultivo id: " + cultivoId);
        }

        Cultivo cultivo = cultivoRepository.findById(cultivoId)
                .orElseThrow(() -> new ResourceNotFoundException("Cultivo id: " + cultivoId + " not found"));

        Fertilizante fertilizante = fertilizanteRepository.findById(fertilizanteId)
                .orElseThrow(() -> new ResourceNotFoundException("Fertilizante id: " + fertilizanteId + " not found"));

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
            throw new ResourceNotFoundException("Relación Cultivo-Fertilizante id: " + id + " not found");
        }
        cultivoFertilizante.setId(id);
        return cultivoFertilizanteRepository.save(cultivoFertilizante);
    }

    @Override
    public void deleteById(Long id) {
        if (!cultivoFertilizanteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Relación Cultivo-Fertilizante id: " + id + " not found");
        }
        cultivoFertilizanteRepository.deleteById(id);
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