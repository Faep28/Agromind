package pe.edu.upc.backend.Services;

import pe.edu.upc.backend.Entities.Authority;

import java.util.List;

public interface AuthorityService {
    //CRUD lll
    public Authority add(Authority authority);
    public List<Authority> findAll();
    public Authority edit(Authority authority);



}
