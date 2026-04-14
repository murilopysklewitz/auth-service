package com.microsservice.auth.infra.persistence;

import com.microsservice.auth.domain.User;
import com.microsservice.auth.domain.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class JpaUserRepository implements UserRepository {
    private final SpringJpaUserRepository repository;

    public JpaUserRepository(SpringJpaUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email).map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findById(String id) {
        return repository.findById(UUID.fromString(id)).map(UserMapper::toDomain);
    }

    @Override
    public User save(User user) {
        UserJpaEntity entity = UserMapper.toEntity(user);
        UserJpaEntity saved = repository.save(entity);
        return UserMapper.toDomain(saved);
    }
}
