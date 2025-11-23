package pe.edu.upc.backend.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.backend.Entities.Noticia;
import pe.edu.upc.backend.Repositories.NoticiasRepository;
import pe.edu.upc.backend.Services.NoticiasService;

import java.time.LocalDate;
import java.util.List;

@Service
public class NoticiasServiceImpl implements NoticiasService {

    @Autowired
    private NoticiasRepository noticiasRepository;

    @Override
    public Noticia add (Noticia noticia) {
        return noticiasRepository.save(noticia); //guarda la noticia en la base de datos
    }

    @Override
    public List<Noticia>findAll(){
        return noticiasRepository.findAll(); //devuelve todas las noticias
    }

    @Override
    public Noticia edit(Long id, Noticia noticia) {
        if (!noticiasRepository.existsById(id)) {
            throw new RuntimeException("Noticia no encontrada");
        }
        noticia.setId(id);  // Establecemos el ID para actualizar
        return noticiasRepository.save(noticia);
    }


    @Override
    public void deleteById(Long id) {
        if (noticiasRepository.existsById(id)) {
            noticiasRepository.deleteById(id);  // Eliminamos la noticia si existe
        } else {
            throw new RuntimeException("Noticia no encontrada");
        }
    }


    // Implementación del nuevo método para obtener noticias después de una fecha
    @Override
    public List<Noticia> findNoticiasAfterFecha(LocalDate fecha) {
        return noticiasRepository.findNoticiasAfterFecha(fecha);
    }

}
