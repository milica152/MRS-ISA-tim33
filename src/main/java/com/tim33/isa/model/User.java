package com.tim33.isa.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "username", nullable = false)
    //@NotEmpty(message = "Username may not be empty")
    private String username;

    @Column(name = "password", nullable = false)
    //@NotEmpty(message = "Password may not be empty")
    private String password;

    @Column(name = "ime", nullable = false)
    //@NotEmpty(message = "First name may not be empty")
    private String ime;

    @Column(name = "prezime", nullable = false)
    //@NotEmpty(message = "Last name may not be empty")
    private String prezime;

    @Column(name = "email", nullable = false)
    //@NotEmpty(message = "Email may not be empty")
    //@Email(message="Email format is not correct.")
    private String email;

    @Column(name = "tip_korisnika", nullable = false)
    private TipUsera tip_korisnika;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<UserRole> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.addAll(roles);
        return grantedAuthorities;
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