package pe.edu.upc.backend.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.backend.entities.Notificacion;
import pe.edu.upc.backend.exceptions.ResourceNotFoundException;
import pe.edu.upc.backend.repositories.NotificacionRepository;
import pe.edu.upc.backend.services.NotificacionService;

import java.util.List;

@Service
public class NotificacionServiceImpl implements NotificacionService {

    @Autowired
    private NotificacionRepository notificacionRepository;

    @Override
    public Notificacion add(Notificacion notificacion) {
        return notificacionRepository.save(notificacion);
    }

    @Override
    public List<Notificacion> findAll() {
        return notificacionRepository.findAll();
    }

    @Override
    public Notificacion edit(Notificacion notificacion) {
        if (!notificacionRepository.existsById(notificacion.getId())) {
            throw new ResourceNotFoundException("Notificación id: " + notificacion.getId() + " not found");
        }
        return notificacionRepository.save(notificacion);
    }

    @Override
    public void deleteById(Long id) {
        if (!notificacionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Notificación id: " + id + " not found");
        }
        notificacionRepository.deleteById(id);
    }

    @Override
    public List<Notificacion> findByUserId(Long userId) {
        return notificacionRepository.findByUsuarioId(userId);
    }
}
