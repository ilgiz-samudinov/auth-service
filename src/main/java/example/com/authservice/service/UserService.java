package example.com.authservice.service;

import example.com.authservice.exception.NotFoundException;
import example.com.authservice.exception.ValidationException;
import example.com.authservice.model.User;
import example.com.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
    public User createUser(User user){
        if(userRepository.existsByEmail(user.getEmail())){
            throw new ValidationException("Email already in use");
        }
        if(user.getPassword() == null) {
            throw new ValidationException("Password cannot be null");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Transactional(readOnly=true)
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @Transactional
    public User updateUser(Long id, User user){
        if(userRepository.existsByEmailAndIdNot(user.getEmail(), id )){
            throw new ValidationException("Email already in use");
        }
        if(user.getPassword() == null) {
            throw new ValidationException("Password cannot be null");
        }

        User existingUser = getUserById(id);
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        existingUser.setRoles(user.getRoles());
        return userRepository.save(existingUser);
    }

    @Transactional(readOnly = true)
    public User getUserById(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(()->{
                    return new NotFoundException("User not found");
                });
    }

    @Transactional
    public void deleteUser(Long userId){
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("User not found");
        }
        userRepository.deleteById(userId);
    }

}
