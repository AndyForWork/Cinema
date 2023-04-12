package com.example.demo.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Data
public class Client implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min=5, message = "имя должно быть не еменьше 5 символов")
    private String name;
    @NotNull
    @Size(min=5, message = "пароль должен быть не меньше 5 символов")
    private String password;


    @NotNull
    @Transient
    @Size(min=5, message = "пароль должен быть не меньше 5 символов")
    private String passwordConfirm;

    @ManyToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @JoinTable(name="roles_clients", joinColumns = @JoinColumn(name="client_id"), inverseJoinColumns = @JoinColumn(name="role_id"))
    private List<Role> roles = new ArrayList<>();
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return name;
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
