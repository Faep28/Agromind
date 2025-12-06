package pe.edu.upc.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.edu.upc.backend.entities.Noticia;
import pe.edu.upc.backend.entities.User;
import pe.edu.upc.backend.repositories.UserRepository;
import pe.edu.upc.backend.security.JwtUtilService;
import pe.edu.upc.backend.services.NoticiasService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

//@CrossOrigin("*")
@RestController
@RequestMapping("/api/noticias")
public class NoticiaController {

    @Autowired
    private NoticiasService noticiasService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtilService jwtUtilService;

    // -------------------------- CRUD --------------------------
    // Crear una nueva noticia asociada a un usuario

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

    // Nuevo endpoint para obtener noticias después de una fecha específica
    @GetMapping("/after/{fecha}")
    public ResponseEntity<List<Noticia>> getNoticiasAfterFecha(@PathVariable String fecha) {
        try {
            // Convertir la fecha del string a LocalDate
            LocalDate localDate = LocalDate.parse(fecha);

            // Buscar las noticias
            List<Noticia> noticias = noticiasService.findNoticiasAfterFecha(localDate);

            if (noticias.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // Si no hay noticias, retornar 204
            }

            return new ResponseEntity<>(noticias, HttpStatus.OK);  // Si hay noticias, retornar 200 OK
        } catch (Exception e) {
            // Manejo de errores, si el formato de fecha es incorrecto
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  // Retorna 400 si la fecha no es válida
        }
    }
    // Crear noticia con imagen

    // Método para crear noticia
    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")  // Solo el admin puede acceder a esta ruta
    public ResponseEntity<Noticia> createNoticia(
            @RequestParam("titulo") String titulo,
            @RequestParam("contenido") String contenido,
            @RequestParam("fechapublicacion") String fechapublicacion,
            @RequestParam("imagen") MultipartFile imagen) throws IOException {

        // Convertir la imagen a byte[] y guardar la noticia
        byte[] imagenBytes = imagen.getBytes();
        Noticia noticia = new Noticia();
        noticia.setTitulo(titulo);
        noticia.setContenido(contenido);
        noticia.setFechapublicacion(LocalDate.parse(fechapublicacion));
        noticia.setImagen(imagenBytes);

        // Guardar la noticia
        Noticia savedNoticia = noticiasService.save(noticia);

        return ResponseEntity.ok(savedNoticia);
    }

}

