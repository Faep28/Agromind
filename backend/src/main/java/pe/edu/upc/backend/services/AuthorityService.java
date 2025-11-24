package pe.edu.upc.backend.services;

import pe.edu.upc.backend.entities.Authority;

import java.util.List;

public interface AuthorityService {
    //CRUD lll
    public Authority add(Authority authority);
    public List<Authority> findAll();
    public Authority edit(Authority authority);

    public Authority findById(Long id);
    public Authority findByRoleName(String roleName);

}
