package pe.edu.upc.backend.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.backend.Entities.User;
import pe.edu.upc.backend.Repositories.UserRepository;
import pe.edu.upc.backend.Services.UserService;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
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
