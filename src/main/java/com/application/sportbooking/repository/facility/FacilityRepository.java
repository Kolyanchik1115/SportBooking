package com.application.sportbooking.repository.facility;

import com.application.sportbooking.model.Facility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long> {

    @Query(value =
            "SELECT * FROM facilities f "
            + "WHERE (:sportType IS NULL OR "
            + "       EXISTS ( "
            + "           SELECT 1 FROM unnest(:sportType) AS st "
            + "           WHERE st = ANY (f.sport_type) "
            + ")) "
            + "AND (:facilityType IS NULL OR f.facility_type = :facilityType) "
            + "AND (:coveringType IS NULL OR f.covering_type = :coveringType)",
            nativeQuery = true)
    Page<Facility> findAllWithFilter(
            @Param("sportType") String[] sportType,
            @Param("facilityType") String facilityType,
            @Param("coveringType") String coveringType,
            Pageable pageable);

    @Query("SELECT f FROM Facility f WHERE f.user.id = :ownerId")
    Page<Facility> findOwnerFacilities(Pageable pageable, Long ownerId);
}
