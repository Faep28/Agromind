package pe.edu.upc.backend.Services;

import pe.edu.upc.backend.Entities.Notificacion;

import java.util.List;

public interface NotificacionService {
    Notificacion add(Notificacion notificacion);
    List<Notificacion> findAll();
    Notificacion edit(Notificacion notificacion);
    void deleteById(Long id);
    List<Notificacion> findByUserId(Long userId);
}
