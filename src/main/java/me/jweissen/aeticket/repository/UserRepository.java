package me.jweissen.aeticket.repository;

import me.jweissen.aeticket.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByToken(String token);
    User findByEmail(String email);
}
