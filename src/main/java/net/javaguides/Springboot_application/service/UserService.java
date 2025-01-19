package net.javaguides.Springboot_application.service;

import jakarta.validation.constraints.NotEmpty;
import net.javaguides.Springboot_application.dto.RegistrationDto;
import net.javaguides.Springboot_application.entity.User;

public interface UserService {

    void saveUser(RegistrationDto registrationDto);

    User findByEmail(String email);
}
