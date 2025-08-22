package com.example.karina_project.repository;

import com.example.karina_project.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByLoginId(String loginId);

    Boolean existsByLoginId(String loginId);
}
