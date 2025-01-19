package net.javaguides.Springboot_application.repository;

import net.javaguides.Springboot_application.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRespository extends JpaRepository<User,Long> {
    User findByEmail(String email);
}
