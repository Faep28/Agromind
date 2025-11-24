package pe.edu.upc.backend.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pe.edu.upc.backend.dtos.UserDTO;
import pe.edu.upc.backend.entities.Authority;
import pe.edu.upc.backend.entities.User;
import pe.edu.upc.backend.repositories.UserRepository;
import pe.edu.upc.backend.services.AuthorityService;
import pe.edu.upc.backend.services.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    AuthorityService authorityService;

    private List<Authority> authoritiesFromString(String authorities){

        List<Authority> authorityList = new ArrayList<>();
        List<String> authorityStringList = Arrays.stream(authorities.split(";")).toList();
        for(String authorityString: authorityStringList) {
            Authority authority = authorityService.findByRoleName(authorityString);
            if (authority!=null) {
                authorityList.add(authority);
            }
        }
        return authorityList;
    }
    //----------------------------------CRUD----------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------

    @Override
    public User add(User user) {
        return userRepository.save(user);  // Crear un nuevo usuario
    }
    @Override
    public List<User> findAll() {
        return userRepository.findAll();  // Obtener todos los usuarios
    }

    @Override
    public User edit(User user) {
        // Verifica si el usuario existe primero
        if (userRepository.existsById(user.getId())) {
            return userRepository.save(user);  // Si existe, actualiza el usuario
        }
        return null;  // Si no existe, devuelve null
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDTO add(UserDTO userDTO) {
        List<Authority> authorityList=authoritiesFromString(userDTO.getAuthorities());

        User newUser = new User(null, userDTO.getUsername(),
                new BCryptPasswordEncoder().encode(userDTO.getPassword()),
                null, true,authorityList, null, null, null);

        newUser = userRepository.save(newUser);
        userDTO.setUserId(newUser.getId());
        return userDTO;
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);  // Eliminar un usuario por ID
    }

    @Override
    public List<User> findUsersWithUnreadNotifications() {
        return userRepository.findUsersWithUnreadNotifications();
    }

    @Override
    public List<Object[]> findUsersWithMoreThanNotificationsNative(int minCount) {
        return userRepository.findUsersWithMoreThanNotificationsNative(minCount);
    }



    //----------------------------------CRUD----------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------

}
