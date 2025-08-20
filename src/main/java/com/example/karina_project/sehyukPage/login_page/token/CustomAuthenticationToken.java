package com.example.karina_project.sehyukPage.login_page.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private final String role;

    public CustomAuthenticationToken(Object principal, Object credentials, String role) {
        super(principal, credentials);
        this.role = role;
    }

    public CustomAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);

        if(authorities != null) {
            this.role = authorities.iterator().next().getAuthority();
        }else{
            this.role = null;
        }
    }

    public String getRole() {
        return role;
    }

}
