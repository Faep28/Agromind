package pe.edu.upc.backend.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pe.edu.upc.backend.dtos.UserDTO;
import pe.edu.upc.backend.entities.Authority;
import pe.edu.upc.backend.entities.User;
import pe.edu.upc.backend.exceptions.InvalidDataException;
import pe.edu.upc.backend.exceptions.KeyRepeatedDataException;
import pe.edu.upc.backend.exceptions.RequiredDataException;
import pe.edu.upc.backend.exceptions.ResourceNotFoundException;
import pe.edu.upc.backend.repositories.UserRepository;
import pe.edu.upc.backend.services.AuthorityService;
import pe.edu.upc.backend.services.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

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
        throw new ResourceNotFoundException("User id: " + user.getId() + " not found");  // Si no existe, devuelve null
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User id: " + id + " not found"));
    }

    @Override
    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new ResourceNotFoundException("User with username: " + username + " not found");
        }
        return user;
    }

    @Override
    public UserDTO add(UserDTO userDTO) {
        User existingUser = userRepository.findByUsername(userDTO.getUsername());
        if (existingUser != null) {
            throw new KeyRepeatedDataException("Username already exists: " + userDTO.getUsername());
        }

        if (userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
            throw new RequiredDataException("Password is required");
        }

        List<Authority> authorityList = authoritiesFromString(userDTO.getAuthorities());
        if (authorityList.isEmpty()) {
            throw new InvalidDataException("Authorities are invalid or empty");
        }

        User newUser = new User(null, userDTO.getUsername(),
                new BCryptPasswordEncoder().encode(userDTO.getPassword()),
                userDTO.getEmail(), true, authorityList, null,
                null, null);

        newUser = userRepository.save(newUser);
        userDTO.setUserId(newUser.getId());
        return userDTO;
    }

    @Override
    public void deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User id: " + id + " not found");
        }
        userRepository.deleteById(id); // Eliminar un usuario por ID
    }

    @Override
    public List<User> findUsersWithUnreadNotifications() {
        return userRepository.findUsersWithUnreadNotifications();
    }

    @Override
    public List<Object[]> findUsersWithMoreThanNotificationsNative(int minCount) {
        return userRepository.findUsersWithMoreThanNotificationsNative(minCount);
    }
}
