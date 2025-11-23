package pe.edu.upc.backend.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.backend.entities.Authority;
import pe.edu.upc.backend.services.AuthorityService;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/authorities") //http://localhost:8080/api/authorities
public class AuthorityController {
    @Autowired
    private AuthorityService authorityService;
    // --------------------------CRUD----------------------------------------------
    //---------------------------------------------------------------------------------------------------------------
    // Crear un nuevo rol
    @PostMapping("/insert")  //http://localhost:8080/api/authorities/insert
    public ResponseEntity<Authority> addAuthority(@RequestBody Authority authority) {
        Authority createdAuthority = authorityService.add(authority);
        return new ResponseEntity<>(createdAuthority, HttpStatus.CREATED);
    }
    // Obtener todos los roles
    @GetMapping("/list")  //http://localhost:8080/api/authorities/list
    public List<Authority> getAllAuthorities() {
        return authorityService.findAll();
    }
    // Actualizar un rol
    @PutMapping("/update/{id}") // http://localhost:8080/api/authorities/update/{id}
    public ResponseEntity<Authority> updateAuthority(@PathVariable Long id, @RequestBody Authority authorityDetails) {
        authorityDetails.setId(id);  // Establecemos el ID del rol desde la URL
        Authority updatedAuthority = authorityService.edit(authorityDetails);
        if (updatedAuthority != null) {
            return new ResponseEntity<>(updatedAuthority, HttpStatus.OK);  // Si la actualización es exitosa
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Si el rol no fue encontrado
        }
    }

    // --------------------------CRUD----------------------------------------------
    //---------------------------------------------------------------------------------------------------------------

}
