package com.microsservice.auth.app;

import com.microsservice.auth.app.registerUserUseCase.RegisterUserRequest;
import com.microsservice.auth.app.registerUserUseCase.RegisterUserResponse;
import com.microsservice.auth.domain.User;
import com.microsservice.auth.domain.UserRole;
import com.microsservice.auth.domain.UserStatus;
import com.microsservice.auth.infra.persistence.user.UserJpaEntity;
import com.microsservice.auth.infra.persistence.user.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class UserMapperTest {


    private UUID userId;
    private String email;
    private String passwordHash;
    private UserRole role;
    private UserStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        email = "test@example.com";
        passwordHash = "hashed_password_123";
        role = UserRole.USER;
        status = UserStatus.ACTIVE;
        createdAt = LocalDateTime.now().minusHours(1);
        updatedAt = LocalDateTime.now();
    }

    @Test
    void shouldMapUserJpaEntityToDomain() {
        UserJpaEntity entity = new UserJpaEntity(userId, email, passwordHash, role, status, createdAt, updatedAt);

        User user = UserMapper.toDomain(entity);

        assertNotNull(user);
        assertEquals(userId, user.getId());
        assertEquals(email, user.getEmail());
        assertEquals(passwordHash, user.getPasswordHash());
        assertEquals(role, user.getRole());
        assertEquals(status, user.getStatus());
        assertEquals(createdAt, user.getCreatedAt());
        assertEquals(updatedAt, user.getUpdatedAt());
    }

    @Test
    void shouldMapDomainUserToJpaEntity() {
        User user = User.restore(userId, email, passwordHash, role, status, createdAt, updatedAt);

        UserJpaEntity entity = UserMapper.toEntity(user);

        assertNotNull(entity);
        assertEquals(userId, entity.getId());
        assertEquals(email, entity.getEmail());
        assertEquals(passwordHash, entity.getPasswordHash());
        assertEquals(role, entity.getRole());
        assertEquals(status, entity.getStatus());
        assertEquals(createdAt, entity.getCreatedAt());
        assertEquals(updatedAt, entity.getUpdatedAt());
    }

    @Test
    void shouldPreserveAllFieldsWhenMappingEntityToDomain() {
        UserJpaEntity entity = new UserJpaEntity(userId, email, passwordHash, UserRole.ADMIN, UserStatus.BLOCKED, createdAt, updatedAt);
        User user = UserMapper.toDomain(entity);

        assertEquals(entity.getId(), user.getId());
        assertEquals(entity.getEmail(), user.getEmail());
        assertEquals(entity.getPasswordHash(), user.getPasswordHash());
        assertEquals(entity.getRole(), user.getRole());
        assertEquals(entity.getStatus(), user.getStatus());
        assertEquals(entity.getCreatedAt(), user.getCreatedAt());
        assertEquals(entity.getUpdatedAt(), user.getUpdatedAt());
    }

    @Test
    void shouldPreserveAllFieldsWhenMappingDomainToEntity() {
        User user = User.restore(userId, email, passwordHash, UserRole.ADMIN, UserStatus.BLOCKED, createdAt, updatedAt);

        UserJpaEntity entity = UserMapper.toEntity(user);

        assertEquals(user.getId(), entity.getId());
        assertEquals(user.getEmail(), entity.getEmail());
        assertEquals(user.getPasswordHash(), entity.getPasswordHash());
        assertEquals(user.getRole(), entity.getRole());
        assertEquals(user.getStatus(), entity.getStatus());
        assertEquals(user.getCreatedAt(), entity.getCreatedAt());
        assertEquals(user.getUpdatedAt(), entity.getUpdatedAt());
    }

    @Test
    void shouldHandleNullIdInMapping() {
        User user = User.restore(null, email, passwordHash, role, status, createdAt, updatedAt);

        UserJpaEntity entity = UserMapper.toEntity(user);

        assertNull(entity.getId());
        assertEquals(email, entity.getEmail());
    }

    @Test
    void shouldHandleDifferentUserRoles() {
        for (UserRole testRole : UserRole.values()) {
            UserJpaEntity entity = new UserJpaEntity(userId, email, passwordHash, testRole, status, createdAt, updatedAt);

            User user = UserMapper.toDomain(entity);

            assertEquals(testRole, user.getRole());
        }
    }

    @Test
    void shouldHandleDifferentUserStatuses() {
        for (UserStatus testStatus : UserStatus.values()) {
            UserJpaEntity entity = new UserJpaEntity(userId, email, passwordHash, role, testStatus, createdAt, updatedAt);

            User user = UserMapper.toDomain(entity);

            assertEquals(testStatus, user.getStatus());
        }
    }

    @Test
    void shouldBidirectionalMapping() {
        UserJpaEntity originalEntity = new UserJpaEntity(userId, email, passwordHash, role, status, createdAt, updatedAt);

        User user = UserMapper.toDomain(originalEntity);
        UserJpaEntity remappedEntity = UserMapper.toEntity(user);

        assertEquals(originalEntity.getId(), remappedEntity.getId());
        assertEquals(originalEntity.getEmail(), remappedEntity.getEmail());
        assertEquals(originalEntity.getPasswordHash(), remappedEntity.getPasswordHash());
        assertEquals(originalEntity.getRole(), remappedEntity.getRole());
        assertEquals(originalEntity.getStatus(), remappedEntity.getStatus());
        assertEquals(originalEntity.getCreatedAt(), remappedEntity.getCreatedAt());
        assertEquals(originalEntity.getUpdatedAt(), remappedEntity.getUpdatedAt());
    }

    @Test
    void shouldHandleBlockedUser() {
        UserJpaEntity blockedEntity = new UserJpaEntity(userId, email, passwordHash, role, UserStatus.BLOCKED, createdAt, updatedAt);

        User user = UserMapper.toDomain(blockedEntity);

        assertTrue(user.isBlocked());
        assertFalse(user.isActivate());
    }

    @Test
    void shouldHandleActiveUser() {
        UserJpaEntity activeEntity = new UserJpaEntity(userId, email, passwordHash, role, UserStatus.ACTIVE, createdAt, updatedAt);

        User user = UserMapper.toDomain(activeEntity);

        assertTrue(user.isActivate());
        assertFalse(user.isBlocked());
    }

    @Test
    void shouldMapMultipleUsersIndependently() {
        UUID userId2 = UUID.randomUUID();
        UserJpaEntity entity1 = new UserJpaEntity(userId, email, passwordHash, role, status, createdAt, updatedAt);
        UserJpaEntity entity2 = new UserJpaEntity(userId2, "other@example.com", "other_password", UserRole.ADMIN, UserStatus.BLOCKED, createdAt, updatedAt);

        User user1 = UserMapper.toDomain(entity1);
        User user2 = UserMapper.toDomain(entity2);

        assertNotEquals(user1.getId(), user2.getId());
        assertNotEquals(user1.getEmail(), user2.getEmail());
        assertNotEquals(user1.getRole(), user2.getRole());
        assertNotEquals(user1.getStatus(), user2.getStatus());
    }
}
