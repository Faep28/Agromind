package pe.edu.upc.backend.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.backend.entities.Authority;
import pe.edu.upc.backend.exceptions.ResourceNotFoundException;
import pe.edu.upc.backend.repositories.AuthorityRepository;
import pe.edu.upc.backend.services.AuthorityService;

import java.util.List;

@Service
public class AuthorityServiceImpl implements AuthorityService {
    @Autowired
    private AuthorityRepository authorityRepository;
    //----------------------------------CRUD----------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------

    @Override
    public Authority add(Authority authority) {
        return authorityRepository.save(authority);  // Crear un nuevo rol
    }
    @Override
    public List<Authority> findAll() {
        return authorityRepository.findAll();  // Obtener todos los roles
    }

    @Override
    public Authority edit(Authority authority) {
        if (!authorityRepository.existsById(authority.getId())) {
            throw new ResourceNotFoundException("Authority id: " + authority.getId() + " not found");
        }
        return authorityRepository.save(authority);
    }

    @Override
    public Authority findById(Long id) {
        return authorityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Authority id: " + id + " not found"));
    }

    @Override
    public Authority findByRoleName(String roleName) {
        Authority authority = authorityRepository.findByRoleName(roleName);
        if (authority == null) {
            throw new ResourceNotFoundException("Authority roleName: " + roleName + " not found");
        }
        return authority;
    }
}
