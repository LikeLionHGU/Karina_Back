package com.example.karina_project.sehyukPage.login_page;

import com.example.karina_project.sehyukPage.login_page.service.CustomUserDetailService;
import com.example.karina_project.sehyukPage.login_page.token.CustomAuthenticationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final CustomUserDetailService customUserDetailService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        CustomAuthenticationToken token = (CustomAuthenticationToken) authentication;

        String username = token.getPrincipal().toString();
        String password = token.getCredentials().toString();
        String rawMRole = token.getRole();

        CustomUserDetail customUserDetail = (CustomUserDetail) customUserDetailService.loadUserByUsername(username);

        if (customUserDetail == null) {
            throw new BadCredentialsException("Invalid ID");
        }

        if (!passwordEncoder.matches(password, customUserDetail.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        String role = "ROLE_" + rawMRole.toUpperCase();

        boolean isClassificationValid = customUserDetail.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(role));

        if (!isClassificationValid) {
            throw new BadCredentialsException("Invalid role");
        }
        return new CustomAuthenticationToken(customUserDetail, null, customUserDetail.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CustomAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
