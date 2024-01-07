package com.atomize.entity;

import com.atomize.dtos.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

@Table(name = "Dos")
@Entity
@Data
@RequiredArgsConstructor
@Builder
public class Dos implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;
    @Column(nullable = false)
    private final String name;
    @Pattern(regexp = "\\+\\d{12}", message = "Invalid phone number")
    private final String phoneNumber;
    @Column(nullable = false, unique = true, updatable = false)
    @Email
    @Pattern(regexp = ".+@.+\\..+")
    private final String email;
    @Column(nullable = false, unique = true, updatable = false)
    private final String schoolName;
    @Column(nullable = false)
    @Size(min = 4)

    private final String password;

    private final Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));

    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
