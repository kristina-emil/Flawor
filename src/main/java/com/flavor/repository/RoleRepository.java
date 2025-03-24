package com.flavor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.flavor.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
