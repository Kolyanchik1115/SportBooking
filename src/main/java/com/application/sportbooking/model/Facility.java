
package com.application.sportbooking.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.type.SqlTypes;

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
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private boolean isDeleted = false;

    public enum CoveringType {
        artificial_lawn, natural_lawn,
        parquet, rubber, sand
    }

    public enum FacilityType {
        indoor,
        outdoor
    }

    public enum SportType {
        basketball, soccer,
        tennis, volleyball
    }
}
