package com.example.dream_of_refrigerator.repository.user;

import com.example.dream_of_refrigerator.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(@Param("email") String email);

    Optional<User> findByNickname(@Param("nickname") String nickname);
    Optional<User> findById(@Param("id")Long id);
}
