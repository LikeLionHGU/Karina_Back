package com.example.karina_project.repository;

import com.example.karina_project.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByLoginId(String loginId);

    Optional<User> findById(long id);
    Boolean existsByLoginId(String loginId);
}
