package code.tofu.useSecurity.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import code.tofu.useSecurity.service.JWTService;
import code.tofu.useSecurity.service.UserDetailsServiceImpl;

import java.io.IOException;
import java.util.Optional;

@Component
@Slf4j
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    UserDetailsServiceImpl userDetailsSvc;

    @Autowired
    JWTService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Claims claims = jwtService.extractAndValidateToken(request);
        if (null == claims) {
            log.info("JWT Claims is Empty");
            filterChain.doFilter(request, response);
            return;
        }

        UserDetails userDetails = userDetailsSvc.loadUserById(claims.getSubject());
        if (null == userDetails) {
            log.info("User Details is Null");
            filterChain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null,userDetails.getAuthorities() ); //list of authorities created in impl class
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }

}
