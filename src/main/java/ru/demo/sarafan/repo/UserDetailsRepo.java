package ru.demo.sarafan.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.demo.sarafan.domain.User;

public interface UserDetailsRepo extends JpaRepository<User, String> {
}