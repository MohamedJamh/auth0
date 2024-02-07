package com.auth0.repository;

import com.auth0.domain.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    @Query("SELECT p FROM Permission p WHERE p.subject = :subject AND p.action = :action")
    Optional<Permission> findBySubjectAndAction(String subject, String action);
}
