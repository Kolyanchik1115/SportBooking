
package com.application.sportbooking.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Data
@SQLDelete(sql = "UPDATE facilities SET is_deleted = true WHERE id=?")
@SQLRestriction("is_deleted=false")
@Table(name = "facilities")
public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private Set<SportType> sportType;

    @Enumerated(EnumType.STRING)
    private CoveringType coveringType;

    @Enumerated(EnumType.STRING)
    private FacilityType facilityType;

    private String location;
    private String address;
    private String description;
    private int minBookingTime;
    private BigDecimal avgPrice;
    private boolean isWorking = false;
    private boolean isRemoved = false;

    @OneToMany(mappedBy = "facility", fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<FacilityImage> facilityImages;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;

    @OneToMany(mappedBy = "facility")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Favorite> favoriteBy = new HashSet<>();

    @Column(nullable = false)
    private boolean isDeleted = false;

    public enum CoveringType {
        artificial_lawn, natural_lawn, parquet, rubber, sand
    }

    public enum FacilityType {
        indoor, outdoor
    }

    public enum SportType {
        basketball, soccer, tennis, volleyball
    }
}
