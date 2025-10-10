package pe.edu.upc.backend.Service;

import pe.edu.upc.backend.Entitie.Noticia;
import java.util.List;

public interface NoticiasService {

    Noticia add(Noticia noticia);  // Registrar una nueva noticia
    List<Noticia> findAll();       // Listar todas las noticias
    Noticia edit(Long id, Noticia noticia);  // Modificar una noticia
    void deleteById(Long id);      // Borrar una noticia
}


