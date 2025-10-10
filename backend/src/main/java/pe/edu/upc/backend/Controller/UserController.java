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
@RequestMapping("/api/users")  //http://localhost:8080/api/users
public class UserController {
    @Autowired
    private UserService userService;
    // --------------------------CRUD----------------------------
    //---------------------------------------------------------------------------------------------------------------
    // Crear un nuevo usuario
    @PostMapping("/insert")  //http://localhost:8080/api/users/insert
    public ResponseEntity<User> add(@RequestBody User user) {
        User createdUser = userService.add(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
    // Obtener todos los usuarios
    @GetMapping("/list")  //http://localhost:8080/api/users/list
    public List<User> getAllUsers() {
        return userService.findAll();
    }
    @PutMapping("/update/{id}")   //http://localhost:8080/api/users/update/{id}
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        // Asegurarse de que el ID de la URL coincida con el ID del objeto del cuerpo
        userDetails.setId(id);  // Establecemos el ID del usuario desde la URL
        User updatedUser = userService.edit(userDetails);  // Llamamos al servicio para actualizar el usuario
        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);  // Si la actualización es exitosa
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Si el usuario no fue encontrado
        }
    }
    // Eliminar un usuario
    @DeleteMapping("/delete/{id}")  //http://localhost:8080/api/users/delete/{id}
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    //----------------------------------CRUD----------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------


}
