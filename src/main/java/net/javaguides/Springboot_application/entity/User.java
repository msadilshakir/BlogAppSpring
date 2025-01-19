package net.javaguides.Springboot_application.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    //FetchType.EAGER : Indicates that roles should be loaded immediately with the User entity.
    //Propagates all persistence operations (e.g., persist, merge, remove) from the User entity to the associated Role entities.
            @JoinTable(name = "users_roles",
                   joinColumns = {@JoinColumn(name = "users_id", referencedColumnName = "id")},
                    inverseJoinColumns = {@JoinColumn(name = "roles_id", referencedColumnName = "id")}
            )

    List<Role> roles = new ArrayList<>();

}
