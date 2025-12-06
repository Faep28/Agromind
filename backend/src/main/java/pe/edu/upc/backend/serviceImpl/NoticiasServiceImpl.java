package pe.edu.upc.backend.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.backend.entities.Noticia;
import pe.edu.upc.backend.exceptions.ResourceNotFoundException;
import pe.edu.upc.backend.repositories.NoticiasRepository;
import pe.edu.upc.backend.services.NoticiasService;

import java.time.LocalDate;
import java.util.List;

@Service
public class NoticiasServiceImpl implements NoticiasService {

    @Autowired
    private NoticiasRepository noticiasRepository;

    @Override
    public Noticia save(Noticia noticia) {
        // Guardar la noticia con la imagen
        return noticiasRepository.save(noticia);
    }

    @Override
    public List<Noticia>findAll(){
        return noticiasRepository.findAll(); //devuelve todas las noticias
    }

    @Override
    public Noticia edit(Long id, Noticia noticia) {
        if (!noticiasRepository.existsById(id)) {
            throw new ResourceNotFoundException("Noticia id: " + id + " not found");
        }
        noticia.setId(id);
        return noticiasRepository.save(noticia);
    }

    @Override
    public void deleteById(Long id) {
        if (!noticiasRepository.existsById(id)) {
            throw new ResourceNotFoundException("Noticia id: " + id + " not found");
        }
        noticiasRepository.deleteById(id);
    }


    // Implementación del nuevo método para obtener noticias después de una fecha
    @Override
    public List<Noticia> findNoticiasAfterFecha(LocalDate fecha) {
        return noticiasRepository.findNoticiasAfterFecha(fecha);
    }

}
