package net.javaguides.Springboot_application.repository;

import net.javaguides.Springboot_application.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(String name);
}
