package code.tofu.useSecurity.service;

import code.tofu.useSecurity.entity.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import code.tofu.useSecurity.repository.UserDetailsRepository;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserDetailsRepository userDetailsRepository;

    public UserDetails createNewUser(UserDetails newUser){
        return userDetailsRepository.save(newUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDetailsRepository.findByUsername(username);
    }

    public UserDetails loadUserById(String userId) {
        Optional<UserDetailsImpl> user = userDetailsRepository.findById(Long.parseLong(userId));
        return user.orElse(null);
    }
}