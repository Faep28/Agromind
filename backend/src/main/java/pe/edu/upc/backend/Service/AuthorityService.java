package pe.edu.upc.backend.Service;

import pe.edu.upc.backend.Entitie.Authority;

import java.util.List;

public interface AuthorityService {
    //CRUD xd
    public Authority add(Authority authority);
    public List<Authority> findAll();
    public Authority edit(Authority authority);



}
