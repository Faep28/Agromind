package pe.edu.upc.backend.Service;

import pe.edu.upc.backend.Entitie.User;

import java.util.List;

public interface UserService {
    //CRUD
    public User add(User user);
    public List<User> findAll();
    public User edit(User user);
    void deleteById(Long id);

    List<User> findUsersWithUnreadNotifications();
    List<Object[]> findUsersWithMoreThanNotificationsNative(int minCount);
}
