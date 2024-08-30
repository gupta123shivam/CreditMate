package com.shivam.CreditMate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shivam.CreditMate.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
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
    @Builder.Default
    @Column(name = "uuid", nullable = false, unique = true)
    private final String uuid = UUID.randomUUID().toString();// Separate UUID for unique business identifier
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Auto-generated Long primary key
    @Column(name = "full_name")
    private String fullname;

    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", unique = true, length = 100, nullable = false)
    private String email;

    @Column(name = "username")
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "logged_in")
    private boolean loggedIn;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CreditCard> creditCards = Collections.emptyList(); // Initialize to prevent NPE

    // Automatically set UUID when the entity is first persisted
    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.username = this.email;
        this.loggedIn = false;
    }

    // Override methods from UserDetails for Spring Security
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
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
