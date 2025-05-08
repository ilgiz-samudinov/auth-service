package example.com.authservice.controller;

import example.com.authservice.dto.AuthRequest;
import example.com.authservice.dto.AuthResponse;
import example.com.authservice.service.CustomUserDetailsService;
import example.com.authservice.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(authRequest.getUsername());
        String token = jwtService.generateToken(userDetails);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
