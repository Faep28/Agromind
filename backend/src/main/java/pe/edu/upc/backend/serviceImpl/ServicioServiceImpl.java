package pe.edu.upc.backend.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.backend.entities.Servicio;
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
            throw new RuntimeException("Servicio no encontrado");
        }
        servicio.setId(id);  // Establecer el ID para actualizar
        return servicioRepository.save(servicio);
    }

    @Override
    public void deleteById(Long id) {
        if (servicioRepository.existsById(id)) {
            servicioRepository.deleteById(id);  // Eliminar el servicio si existe
        } else {
            System.out.println("Servicio con ID " + id + " no encontrado.");
        }
    }


}
