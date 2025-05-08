package example.com.authservice.service;

import example.com.authservice.config.UserDetailsImpl;
import example.com.authservice.exception.NotFoundException;
import example.com.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(UserDetailsImpl::new)
                .orElseThrow(()-> new NotFoundException("User not found"));
    }
}
