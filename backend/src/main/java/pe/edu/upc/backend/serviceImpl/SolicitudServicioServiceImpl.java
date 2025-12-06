package pe.edu.upc.backend.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.backend.entities.Cultivo;
import pe.edu.upc.backend.entities.Servicio;
import pe.edu.upc.backend.entities.SolicitudServicio;
import pe.edu.upc.backend.exceptions.ResourceNotFoundException;
import pe.edu.upc.backend.repositories.CultivoRepository;
import pe.edu.upc.backend.repositories.ServicioRepository;
import pe.edu.upc.backend.repositories.SolicitudServicioRepository;
import pe.edu.upc.backend.services.SolicitudServicioService;

import java.util.List;

@Service
public class SolicitudServicioServiceImpl implements SolicitudServicioService {


    @Autowired
    private SolicitudServicioRepository solicitudServicioRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private CultivoRepository cultivoRepository;

    // Crear una nueva solicitud de servicio
    @Override
    public SolicitudServicio add(Long servicioId, Long cultivoId, SolicitudServicio solicitudServicio) {

        // Buscar el servicio y el cultivo asociados por ID
        Servicio servicio = servicioRepository.findById(servicioId)
                .orElseThrow(() -> new ResourceNotFoundException("Servicio id: " + servicioId + " not found"));
        Cultivo cultivo = cultivoRepository.findById(cultivoId)
                .orElseThrow(() -> new ResourceNotFoundException("Cultivo id: " + cultivoId + " not found"));

        // Asociar la solicitud con el servicio y el cultivo
        solicitudServicio.setServicio(servicio);
        solicitudServicio.setCultivo(cultivo);

        // Guardar la solicitud de servicio
        return solicitudServicioRepository.save(solicitudServicio);
    }

    // Obtener todas las solicitudes de servicio
    @Override
    public List<SolicitudServicio> findAll() {
        return solicitudServicioRepository.findAll();
    }

    // Editar una solicitud de servicio
    @Override
    public SolicitudServicio edit(Long id, SolicitudServicio solicitudServicio) {
        if (!solicitudServicioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Solicitud de servicio id: " + id + " not found");
        }
        solicitudServicio.setId(id);
        return solicitudServicioRepository.save(solicitudServicio);
    }

    //eliminar
    @Override
    public void deleteById(Long id) {
        if (!solicitudServicioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Solicitud de servicio id: " + id + " not found");
        }

        // Eliminar la solicitud de servicio si existe
        solicitudServicioRepository.deleteById(id);
    }
}
