package pe.edu.upc.backend.Service;

import pe.edu.upc.backend.Entitie.Notificacion;

import java.util.List;

public interface NotificacionService {

    Notificacion registrar(Notificacion notificacion);  // Registrar una nueva notificación
    List<Notificacion> listarTodas();  // Listar todas las notificaciones existentes
    Notificacion actualizar(Notificacion notificacion);  // Modificar una notificación
    void eliminarPorId(Long id);  // Borrar una notificación
}

