package com.colindalepass.config;

import com.colindalepass.entity.Role;
import com.colindalepass.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

public class ColindalePassUserDetails implements UserDetails {


    private User user;

    public ColindalePassUserDetails(User user) {

        this.user = user;
    }


    public User getUser() {
        return this.user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = user.getRoles();

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
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
        return user.isEnabled();
    }

    public String getFullName() {
        return this.user.getFirstName() + " " + this.user.getLastName();
    }


    public void setFirstName(String firstName)
    {
        this.user.setFirstName(firstName);
    }

    public void setLastName(String lastName)
    {
        this.user.setLastName(lastName);
    }


}

