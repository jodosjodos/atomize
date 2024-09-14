package com.atomize.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.atomize.dto.Role;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Table(name = "Dos")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Dos implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Pattern(regexp = "\\+\\d{12}", message = "Invalid phone number")
    private String phoneNumber;
    @Column(nullable = false, unique = true, updatable = false)
    @Email
    @Pattern(regexp = ".+@.+\\..+")
    private String email;
    @Column(nullable = false, unique = true, updatable = false)
    private String schoolName;
    @Column(nullable = false)
    @Size(min = 4)

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name = "teachers")
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "creatorDos", cascade = CascadeType.ALL)
    private List<Teacher> teachers;

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
