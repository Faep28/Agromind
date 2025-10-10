package pe.edu.upc.backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.backend.Entitie.Noticia;
import pe.edu.upc.backend.Entitie.User;
import pe.edu.upc.backend.Repository.UserRepository;
import pe.edu.upc.backend.Service.NoticiasService;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/noticias")
public class NoticiaController {

    @Autowired
    private NoticiasService noticiasService;

    @Autowired
    private UserRepository userRepository;

    // -------------------------- CRUD --------------------------
    // Crear una nueva noticia asociada a un usuario
    @PostMapping("/insert/{userId}")
    public ResponseEntity<Noticia> add(@PathVariable Long userId, @RequestBody Noticia noticia) {
        // Buscar el usuario asociado
        User usuario = userRepository.findById(userId).orElse(null);
        if (usuario == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Usuario no encontrado
        }

        // Asociar usuario y fecha actual
        noticia.setUsuarioRelacionado(usuario);
        noticia.setFechapublicacion(LocalDate.now());

        Noticia createdNoticia = noticiasService.add(noticia);
        return new ResponseEntity<>(createdNoticia, HttpStatus.CREATED);
    }

    // Obtener todas las noticias
    @GetMapping("/list")
    public List<Noticia> getAll() {
        return noticiasService.findAll();
    }

    // Actualizar una noticia existente
    @PutMapping("/update/{id}")
    public ResponseEntity<Noticia> update(@PathVariable Long id, @RequestBody Noticia noticiaDetails) {
        Noticia updatedNoticia = noticiasService.edit(id, noticiaDetails);
        return new ResponseEntity<>(updatedNoticia, HttpStatus.OK);
    }

    // Eliminar una noticia
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        noticiasService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    // -------------------------- CRUD --------------------------
}

