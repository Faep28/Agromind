package pe.edu.upc.backend.services;

import pe.edu.upc.backend.dtos.UserDTO;
import pe.edu.upc.backend.entities.User;

import java.util.List;

public interface UserService {
    //CRUD
    public User add(User user);
    public List<User> findAll();
    public User edit(User user);

    public User findById(Long id);
    public User findByUsername(String username);
    public UserDTO add(UserDTO dtoUser);
    void deleteById(Long id);

    List<User> findUsersWithUnreadNotifications();
    List<Object[]> findUsersWithMoreThanNotificationsNative(int minCount);
}
