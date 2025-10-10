package pe.edu.upc.backend.Service;

import pe.edu.upc.backend.Entitie.Notification;

import java.util.List;

public interface NotificationService {

    Notification add(Notification notification);  // Crear nueva notification
    List<Notification> findAll();  // Obtener todas las notifications
    Notification edit(Notification notification);  // Actualizar notification
    void deleteById(Long id);  // Eliminar notification
    
}
