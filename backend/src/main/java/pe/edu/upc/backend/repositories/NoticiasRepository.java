package pe.edu.upc.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.edu.upc.backend.entities.Noticia;

import java.time.LocalDate;
import java.util.List;

public interface NoticiasRepository extends JpaRepository<Noticia,Long> {

    //PRIMER QUERY NATIVE
    @Query(value = "SELECT * FROM noticias WHERE fechapublicacion > :fecha", nativeQuery = true)
    List<Noticia> findNoticiasAfterFecha(LocalDate fecha);
}
