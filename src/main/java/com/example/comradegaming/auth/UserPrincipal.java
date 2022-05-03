package com.example.comradegaming.auth;

import com.example.comradegaming.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class UserPrincipal implements UserDetails {
    private final User user;

    UserPrincipal(User user) {
        this.user = user;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> role = new ArrayList<>(1);
        role.add(new SimpleGrantedAuthority(user.deliverRole()));
        return role;
    }

    @Override
    public String getPassword() {
        return user.deliverPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        //returnerar bara true så länge
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        //returnerar bara true så länge
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        //returnerar bara true så länge
        return true;
    }

    @Override
    public boolean isEnabled() {
        //returnerar bara true så länge
        return true;
    }
}
