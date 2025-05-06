package example.com.authservice.dto;

import example.com.authservice.model.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private String password;
    private Set<Role> roles;
}
