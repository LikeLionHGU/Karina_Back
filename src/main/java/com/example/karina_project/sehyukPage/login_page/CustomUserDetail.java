package com.example.karina_project.sehyukPage.login_page;

import com.example.karina_project.domain.User;
import lombok.RequiredArgsConstructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
public class CustomUserDetail implements UserDetails {

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = user.getRole();
        if (!role.startsWith("ROLE_")) role = "ROLE_" + role;
        return List.of(new SimpleGrantedAuthority(role));
    }

    public Long getId(){ return user.getId(); }
    @Override public String getPassword() { return user.getPassword(); }
    @Override public String getUsername() { return user.getLoginId(); }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}