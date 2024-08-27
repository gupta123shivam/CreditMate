package com.shivam.CreditMate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shivam.CreditMate.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Auto-generated Long primary key

    private String uuid;  // Separate UUID for unique business identifier

    @Column(name = "full_name")
    private String fullname;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Column(name = "email", unique = true, length = 100, nullable = false)
    private String email;

    @Column(name = "username")
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "logged_in")
    private boolean loggedIn;

    // Automatically set UUID when the entity is first persisted
    @PrePersist
    void onCreate() {
        this.uuid = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.username = this.email;
        this.loggedIn = false;
    }

    // Override methods from UserDetails for Spring Security
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return role.toString();
            }
        });
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
