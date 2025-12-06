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
        Fertilizante existente = fertilizanteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fertilizante id: " + id + " not found"));

        // Solo actualizar los campos editables, NO las relaciones
        existente.setNombre(fertilizante.getNombre());
        existente.setTipo(fertilizante.getTipo());
        existente.setDosisRecomendada(fertilizante.getDosisRecomendada());

        return fertilizanteRepository.save(existente);
    }

    @Override
    public void deleteById(Long id) {
        Fertilizante fertilizante = fertilizanteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fertilizante no encontrado con ID: " + id));

        // Verificar si tiene relaciones (opcional - solo para logging)
        if (fertilizante.getCultivoFertilizantes() != null && !fertilizante.getCultivoFertilizantes().isEmpty()) {
            System.out.println("Eliminando fertilizante '" + fertilizante.getNombre() +
                    "' con " + fertilizante.getCultivoFertilizantes().size() + " relaciones");
        }

        // JPA eliminará automáticamente las relaciones gracias al cascade
        fertilizanteRepository.deleteById(id);
    }
}