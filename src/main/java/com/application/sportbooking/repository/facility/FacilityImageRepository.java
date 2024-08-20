
package com.application.sportbooking.repository.facility;

import com.application.sportbooking.model.FacilityImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacilityImageRepository extends JpaRepository<FacilityImage, Long> {}
