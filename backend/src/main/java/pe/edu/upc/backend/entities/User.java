package pe.edu.upc.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;
    private boolean enabled;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="users_authorities",
            joinColumns = {
                    @JoinColumn(
                            name="user_id",
                            referencedColumnName = "id",
                            nullable = false
                    )
            },
            inverseJoinColumns = {
                    @JoinColumn(
                            name = "authority_id",
                            referencedColumnName = "id",
                            nullable = false
                    )
            }
    )
    private List<Authority> authorities;

    // Relación OneToMany con Cliente

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Cliente> clientes;

    // Asociación de uno a muchos con la entidad notificación
    @ToString.Exclude
    @JsonIgnore
    @OneToMany(
            mappedBy = "usuario",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    private List<Notificacion>notificaciones;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "usuarioRelacionado", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Noticia> noticias;

}

