package net.javaguides.Springboot_application.security;

import net.javaguides.Springboot_application.entity.User;
import net.javaguides.Springboot_application.repository.UserRespository;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class CustomomUserDetailsService implements UserDetailsService {

    private UserRespository userRespository;

    public CustomomUserDetailsService(UserRespository userRespository) {
        this.userRespository = userRespository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRespository.findByEmail(email);

        if (user != null) {
            org.springframework.security.core.userdetails.User authenticatedUser
                    = new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    user.getRoles().stream()
                            .map(role -> new SimpleGrantedAuthority(role.getName()))
                            .collect(Collectors.toList())
            );
            return authenticatedUser;
        }else {
            throw  new UsernameNotFoundException("Invalid Username and Password");
        }


    }
}
