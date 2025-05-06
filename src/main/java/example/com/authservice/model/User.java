package example.com.authservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name= "users")
@AllArgsConstructor
@Builder
@Setter
@Getter
@NoArgsConstructor
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable =false)
    private String username;
    @Column (nullable = false, unique = true)
    private String email;
    @Column (nullable = false)
    private String password;


    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles",joinColumns = @JoinColumn(name="user_id"))
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
}
