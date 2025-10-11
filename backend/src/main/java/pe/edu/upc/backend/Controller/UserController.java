package pe.edu.upc.backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.backend.Entitie.User;
import pe.edu.upc.backend.Service.UserService;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/users")  // Base URL: http://localhost:8080/api/users
public class UserController {

    @Autowired
    private UserService userService;

    //----------------------------------CRUD----------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------

    // Crear un nuevo usuario
    @PostMapping("/insert")  // POST → http://localhost:8080/api/users/insert
    public ResponseEntity<User> add(@RequestBody User user) {
        User createdUser = userService.add(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    // Obtener todos los usuarios
    @GetMapping("/list")  // GET → http://localhost:8080/api/users/list
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    // Actualizar un usuario existente
    @PutMapping("/update/{id}")   // PUT → http://localhost:8080/api/users/update/{id}
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        userDetails.setId(id);
        User updatedUser = userService.edit(userDetails);
        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar un usuario
    @DeleteMapping("/delete/{id}")  // DELETE → http://localhost:8080/api/users/delete/{id}
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //----------------------------------CUSTOM QUERIES----------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------

    // Obtener los usuarios que tienen notificaciones NO leídas
    @GetMapping("/unread-notifications")  // GET → http://localhost:8080/api/users/unread-notifications
    public ResponseEntity<List<User>> getUsersWithUnreadNotifications() {
        List<User> users = userService.findUsersWithUnreadNotifications();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // Obtener los usuarios con más de 'X' notificaciones
    @GetMapping("/many-notifications")  // GET → http://localhost:8080/api/users/many-notifications?minCount=2
    public ResponseEntity<List<User>> getUsersWithManyNotifications(
            @RequestParam(defaultValue = "2") int minCount) {
        List<User> users = userService.findUsersWithMoreThanNotifications(minCount);
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
