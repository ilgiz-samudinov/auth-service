package example.com.authservice.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/api")
@RestController
public class PublicController {
    @GetMapping("/public/welcome")
    public String welcome(){
        return "welcome this is auth-service";
    }

    @GetMapping("/authentication/for-authentication")
    public String forAuthentication(){
        return "successfully authenticated";
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/admin/page")
    public String adminPage(){
        return "admin page this is auth-service";
    }

    @GetMapping("/authentication/user")
    public String getAuthenticationUser(@AuthenticationPrincipal UserDetails userDetails){
        return userDetails.getUsername();
    }






}
