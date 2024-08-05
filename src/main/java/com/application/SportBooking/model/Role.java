package com.application.SportBooking.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Data
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private RoleName name;

    @Override
    public String getAuthority() {
        return "ROLE_" + name;
    }

    public enum RoleName {
        USER,
        ADMIN
    }
}
