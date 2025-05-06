package example.com.authservice.controller;

import example.com.authservice.dto.UserRequest;
import example.com.authservice.dto.UserResponse;
import example.com.authservice.mapper.UserMapper;
import example.com.authservice.model.User;
import example.com.authservice.repository.UserRepository;
import example.com.authservice.service.UserService;
import jakarta.persistence.Enumerated;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;


    @PostMapping("/register")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) {
        User user = userMapper.toEntity(userRequest);
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toResponse(createdUser));
    }

    @GetMapping("/all-users")
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return ResponseEntity.ok(
                userService.getAllUsers().stream()
                        .map(userMapper::toResponse)
                        .collect(Collectors.toList())
        );
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(userMapper.toResponse(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, 
                                                   @RequestBody UserRequest userRequest) {
        User user = userMapper.toEntity(userRequest);
        User updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(userMapper.toResponse(updatedUser));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
