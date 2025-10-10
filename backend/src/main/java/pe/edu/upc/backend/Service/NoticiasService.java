package pe.edu.upc.backend.Service;

import pe.edu.upc.backend.Entitie.Noticia;

import java.util.List;

public interface NoticiasService {

    Noticia registrar(Noticia noticia);  // Registrar una nueva noticia
    List<Noticia> listarTodas();  // Listar todas las noticias existentes
    Noticia actualizar(Noticia noticia);  // Modificar una noticia
    void eliminarPorId(Long id);  // Borrar una noticia
}

