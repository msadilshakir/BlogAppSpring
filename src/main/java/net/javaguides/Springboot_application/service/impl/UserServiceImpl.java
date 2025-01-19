package net.javaguides.Springboot_application.service.impl;

import net.javaguides.Springboot_application.dto.RegistrationDto;
import net.javaguides.Springboot_application.entity.Role;
import net.javaguides.Springboot_application.entity.User;
import net.javaguides.Springboot_application.repository.RoleRepository;
import net.javaguides.Springboot_application.repository.UserRespository;
import net.javaguides.Springboot_application.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserServiceImpl implements UserService {

    private UserRespository userRespository;
    private RoleRepository roleRespository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(RoleRepository roleRespository, UserRespository userRespository, PasswordEncoder passwordEncoder) {
        this.roleRespository = roleRespository;
        this.userRespository = userRespository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(RegistrationDto registrationDto) {
        User user = new User();
        user.setName(registrationDto.getFirstName()+" " + registrationDto.getLastName());
        user.setEmail(registrationDto.getEmail());
        //use spring security to encrypt the password and save to DB
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        Role role = roleRespository.findByName("ROLE_GUEST");
        user.setRoles(Arrays.asList(role));
        userRespository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRespository.findByEmail(email);
    }
}
