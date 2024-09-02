package com.application.sportbooking.repository.favorite;

import com.application.sportbooking.model.Facility;
import com.application.sportbooking.model.Favorite;
import com.application.sportbooking.model.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    Optional<Favorite> findByUserAndFacility(User user, Facility facility);

    @Query("SELECT f FROM Facility f JOIN FETCH Favorite fav ON f.id = fav.facility.id "
            + "WHERE fav.user.id = :userId")
    Page<Facility> findFavoriteByUser(Long userId, Pageable pageable);

}
