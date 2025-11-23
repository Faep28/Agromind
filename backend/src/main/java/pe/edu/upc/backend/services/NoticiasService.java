package pe.edu.upc.backend.services;

import pe.edu.upc.backend.entities.Noticia;

import java.time.LocalDate;
import java.util.List;

public interface NoticiasService {

    Noticia add(Noticia noticia);  // Registrar una nueva noticia
    List<Noticia> findAll();       // Listar todas las noticias
    Noticia edit(Long id, Noticia noticia);  // Modificar una noticia
    void deleteById(Long id);      // Borrar una noticia

    //PRIMER QUERY NATIVE
    // Nuevo método para obtener noticias después de una fecha específica
    List<Noticia> findNoticiasAfterFecha(LocalDate fecha);
}


