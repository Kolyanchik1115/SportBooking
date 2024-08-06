package com.application.sportbooking.repository.profile;

import com.application.sportbooking.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles r WHERE u.id = :userId")
    Optional<User> findByUserId(Long userId);
}
