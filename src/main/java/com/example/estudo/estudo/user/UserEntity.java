package com.example.estudo.estudo.user;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private java.util.UUID id;

    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9_]{1,15}$", message = "O login deve conter apenas alphanuméricos e '_' e ter no máximo 15 caracteres.")
    @Column(unique = true)
    private String login;

    @NotBlank
    private String password;

    @NotBlank
    @Pattern(regexp = "^[A-Za-záéíóúâêîôûãõçüÜ\\s]{1,50}$", message = "O nome deve conter apenas letras acentuadas e espaços e ter no máximo 50 caracteres.")
    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;

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
        return login;
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
