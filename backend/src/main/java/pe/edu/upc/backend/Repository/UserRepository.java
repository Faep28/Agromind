package pe.edu.upc.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.edu.upc.backend.Entitie.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    //
    @Query("SELECT DISTINCT u FROM User u JOIN u.notificaciones n WHERE n.esLeido = false")
    List<User> findUsersWithUnreadNotifications();


    // SQL Nativo
    @Query(value = """
        SELECT u.*
        FROM users u
        JOIN notificaciones n ON n.usuario_id = u.id
        GROUP BY u.id
        HAVING COUNT(n.id) > ?1
        """, nativeQuery = true)
    List<User> findUsersWithMoreThanNotifications(int minCount);

}
