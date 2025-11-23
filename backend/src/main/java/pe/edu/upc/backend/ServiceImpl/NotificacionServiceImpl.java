package pe.edu.upc.backend.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.backend.Entities.Notificacion;
import pe.edu.upc.backend.Repositories.NotificacionRepository;
import pe.edu.upc.backend.Services.NotificacionService;

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
        if (notificacionRepository.existsById(notificacion.getId())) {
            return notificacionRepository.save(notificacion);
        }
        return null;
    }

    @Override
    public void deleteById(Long id) {
        if (notificacionRepository.existsById(id)) {
            notificacionRepository.deleteById(id);
        }
    }

    @Override
    public List<Notificacion> findByUserId(Long userId) {
        return notificacionRepository.findByUsuarioId(userId);
    }
}
