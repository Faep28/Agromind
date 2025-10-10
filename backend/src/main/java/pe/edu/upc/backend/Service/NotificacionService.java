package pe.edu.upc.backend.Service;

import pe.edu.upc.backend.Entitie.Notificacion;

import java.util.List;

public interface NotificacionService {
    Notificacion add(Notificacion notificacion);
    List<Notificacion> findAll();
    Notificacion edit(Notificacion notificacion);
    void deleteById(Long id);
    List<Notificacion> findByUserId(Long userId);
}
