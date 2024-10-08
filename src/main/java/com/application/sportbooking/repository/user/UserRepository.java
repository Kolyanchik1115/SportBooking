package com.application.sportbooking.repository.user;

import com.application.sportbooking.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles r WHERE u.id = :userId")
    Optional<User> findByUserId(Long userId);

    Optional<User> findByEmail(String email);
}
