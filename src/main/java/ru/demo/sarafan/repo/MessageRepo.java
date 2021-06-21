package ru.demo.sarafan.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.demo.sarafan.domain.Message;

public interface MessageRepo extends JpaRepository<Message, Long> {

}
