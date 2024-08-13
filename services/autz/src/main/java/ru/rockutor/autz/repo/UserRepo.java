package ru.rockutor.autz.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rockutor.autz.domain.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepo extends JpaRepository<User, UUID> {
    Optional<User> findUserByUsername(String username);
}
