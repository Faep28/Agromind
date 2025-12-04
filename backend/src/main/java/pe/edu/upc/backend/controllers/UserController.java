package pe.edu.upc.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.backend.dtos.TokenDTO;
import pe.edu.upc.backend.dtos.UserDTO;
import pe.edu.upc.backend.entities.User;
import pe.edu.upc.backend.security.JwtUtilService;
import pe.edu.upc.backend.security.UserSecurity;
import pe.edu.upc.backend.services.UserService;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/users")  // Base URL: http://localhost:8080/api/users
public class UserController {

    @Autowired
    UserService  userService;

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

    // Obtener usuarios con más de 'X' notificaciones
    @GetMapping("/many-notifications-native")  // GET → http://localhost:8080/api/users/many-notifications-native?minCount=2
    public ResponseEntity<List<Object[]>> getUsersWithManyNotificationsNative(
            @RequestParam(defaultValue = "2") int minCount) {
        List<Object[]> results = userService.findUsersWithMoreThanNotificationsNative(minCount);

        if (results.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        User foundUser =  userService.findById(id);
        return new ResponseEntity<>(foundUser, HttpStatus.OK);
    }

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtilService jwtUtilService;


    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO user){
        user=userService.add(user);
        return new ResponseEntity<>(user,HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody User user){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword())
        );

        UserSecurity userSecurity = (UserSecurity) userDetailsService.loadUserByUsername(user.getUsername());

        String jwt=jwtUtilService.generateToken(userSecurity);
        Long id = userSecurity.getUser().getId();
        String authorities = userSecurity.getUser().getAuthorities().stream().
                map(authority -> authority.getRoleName())
                .collect(Collectors.joining(";","",""));

        return new ResponseEntity<>(new TokenDTO(jwt, id, authorities), HttpStatus.OK);

    }

}
