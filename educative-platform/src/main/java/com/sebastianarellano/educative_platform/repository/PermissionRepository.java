package com.sebastianarellano.educative_platform.repository;

import com.sebastianarellano.educative_platform.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission,Long> {
}
