package com.microsservice.auth.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringJpaUserRepository extends JpaRepository<UserJpaEntity, UUID>{
    Optional<UserJpaEntity> findByEmail(String email);

}
