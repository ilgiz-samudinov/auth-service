package example.com.authservice;

import example.com.authservice.service.CustomUserDetailsService;
import example.com.authservice.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = extractToken(request);

        if(token != null){
            String username = jwtService.extractUsername(token);
            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                authenticate(username, token);
            }
        }
        filterChain.doFilter(request, response);
    }

    private void authenticate(String username, String token) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        if(jwtService.isTokenValid(token, userDetails)) {
            var authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }


    private String extractToken(HttpServletRequest request){
        if(request.getCookies() == null) return null;
        for(Cookie cookie : request.getCookies()){
            if("access_token".equals(cookie.getName())){
                return cookie.getValue();
            }
        }
        return null;
    }

}
