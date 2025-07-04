package code.tofu.useSecurity.configuration;

import code.tofu.useSecurity.enums.Role;
import code.tofu.useSecurity.security.CustomAccessDeniedHandler;
import code.tofu.useSecurity.security.CustomAuthenticationEntryPoint;
import code.tofu.useSecurity.security.JWTAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import code.tofu.useSecurity.service.UserDetailsServiceImpl;
import static code.tofu.useSecurity.enums.Authority.*;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    JWTAuthorizationFilter jwtAuthorizationFilter;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProviderDAO() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authenticationProvider(authenticationProviderDAO())
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/signup/newUser/**").permitAll()
                        .requestMatchers("/signin").permitAll()
                        .requestMatchers("/refresh").permitAll()
                        .requestMatchers("/except").permitAll()

                        .requestMatchers("/protected").authenticated()
                        //AUTHORITY BASED
//                        .requestMatchers("/protected").hasAnyAuthority(String.valueOf(PROTECTED_AUTHORITY))
                        .requestMatchers("/write").hasAnyAuthority(String.valueOf(WRITE_AUTHORITY))
                        .requestMatchers("/delete").hasAnyAuthority(String.valueOf(DELETE_AUTHORITY))
                        //OR not AND - use hasAuthorityInstead
                        .anyRequest().authenticated())
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                                .accessDeniedHandler(new CustomAccessDeniedHandler()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

}
