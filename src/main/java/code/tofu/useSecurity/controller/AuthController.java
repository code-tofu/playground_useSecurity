package code.tofu.useSecurity.controller;

import code.tofu.useSecurity.enums.Role;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import code.tofu.useSecurity.dto.CredentialsDTO;
import code.tofu.useSecurity.dto.AuthResponseDTO;
import code.tofu.useSecurity.entity.UserDetailsImpl;
import code.tofu.useSecurity.service.JWTService;
import code.tofu.useSecurity.service.UserDetailsServiceImpl;
import static code.tofu.useSecurity.enums.Role.*;

@RestController
@Slf4j
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JWTService jwtService;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/signup/newUser/creator")
    public ResponseEntity<String> signUpNewUserCreator(@RequestBody CredentialsDTO signupRequest){
        UserDetailsImpl savedUser = saveNewUserWithRole(signupRequest,CREATOR);
        log.info("New User Created with CREATOR Role:{}", savedUser.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body("SIGN UP OK"); 
    }

    @PostMapping("/signup/newUser/viewer")
    public ResponseEntity<String> signUpNewUserViewer(@RequestBody CredentialsDTO signupRequest){
        UserDetailsImpl savedUser = saveNewUserWithRole(signupRequest,VIEWER);
        log.info("New User Created with VIEWER Role:{}", savedUser.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body("SIGN UP OK: VIEWER");  
    }

    @PostMapping("/signup/newUser/admin")
    public ResponseEntity<String> signUpNewUserAdmin(@RequestBody CredentialsDTO signupRequest){
        UserDetailsImpl savedUser = saveNewUserWithRole(signupRequest,ADMIN);
        log.info("New User Created with ADMIN Role:{}", savedUser.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body("SIGN UP OK: ADMIN");  
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponseDTO> signin(@RequestBody CredentialsDTO signinRequest){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signinRequest.getUsername(), signinRequest.getPassword()));

        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        log.info("User Authenticated:{}",user.getUserId());

        //Authorities implementation: Role Based
        AuthResponseDTO response = new AuthResponseDTO(jwtService.generateToken(user), jwtService.generateRefreshToken(user),
                String.valueOf(user.getRole()));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/refresh")
    public ResponseEntity<Object> refreshToken(HttpServletRequest httpRequest) {
        Claims claims = jwtService.extractAndValidateToken(httpRequest);
//        if (null == claims) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh Token Required in Auth Header");
//        }
        UserDetailsImpl user = (UserDetailsImpl) userDetailsService.loadUserById(claims.getSubject());
        if (null == user) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh Token Not Valid");
        }
            log.info("User JWT Token Refreshed:{}",user.getUserId());

        final String refreshToken = httpRequest.getHeader(HttpHeaders.AUTHORIZATION).substring(7); //already validated
        AuthResponseDTO response = new AuthResponseDTO(jwtService.generateToken(user), refreshToken,
                String.valueOf(user.getRole()));
        return ResponseEntity.ok(response);
    }


    //to avoid circular dependency with passwordEncoder, unless add another service layer between controller and various services
    public UserDetailsImpl saveNewUserWithRole(CredentialsDTO credentials, Role role){
        UserDetails newUser = UserDetailsImpl.builder()
                .username(credentials.getUsername()).password(passwordEncoder.encode(credentials.getPassword()))
                .enabled(true).accountNonLocked(true).accountNonExpired(true).credentialsNonExpired(true)
                .role(role)
                .build();
        return (UserDetailsImpl) userDetailsService.createNewUser(newUser);
    }


}