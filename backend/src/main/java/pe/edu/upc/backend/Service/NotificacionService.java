package pe.edu.upc.backend.Service;

import pe.edu.upc.backend.Entitie.Noticia;

import java.util.List;

public interface NotificacionService {

    Noticia registrar(Noticia notificacion);  // Registrar una nueva notificación
    List<Noticia> listarTodas();  // Listar todas las notificaciones existentes
    Noticia actualizar(Noticia notificacion);  // Modificar una notificación
    void eliminarPorId(Long id);  // Borrar una notificación
}

