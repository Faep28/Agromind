package pe.edu.upc.backend.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.backend.entities.Servicio;
import pe.edu.upc.backend.exceptions.ResourceNotFoundException;
import pe.edu.upc.backend.repositories.ServicioRepository;
import pe.edu.upc.backend.services.ServicioService;

import java.util.List;

@Service
public class ServicioServiceImpl implements ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;

    // Crear un nuevo servicio
    @Override
    public Servicio add(Servicio servicio) {
        return servicioRepository.save(servicio);
    }

    // Obtener todos los servicios
    @Override
    public List<Servicio> findAll() {
        return servicioRepository.findAll();
    }

    // Editar un servicio
    @Override
    public Servicio edit(Long id, Servicio servicio) {
        if (!servicioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Servicio id: " + id + " not found");
        }
        servicio.setId(id);
        return servicioRepository.save(servicio);
    }


    @Override
    public void deleteById(Long id) {
        if (!servicioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Servicio id: " + id + " not found");
        }
        servicioRepository.deleteById(id);
    }
}
