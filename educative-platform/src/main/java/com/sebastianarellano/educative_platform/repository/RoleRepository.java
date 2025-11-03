package com.sebastianarellano.educative_platform.repository;

import com.sebastianarellano.educative_platform.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
}
