package ru.dictation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dictation.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
