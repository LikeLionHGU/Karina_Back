package com.example.karina_project.sehyukPage.login_page.jwt;

import com.example.karina_project.domain.User;
import com.example.karina_project.sehyukPage.login_page.CustomUserDetail;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtill jwtUtill;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            System.out.println("token null");
            filterChain.doFilter(request, response);

            return;
        }

        System.out.println("authorization now");

        String token = authorization.split(" ")[1];

        if(jwtUtill.isExpired(token)) {
            System.out.println("token expired");
            response.sendRedirect("/");

            return;
        }

        String username = jwtUtill.getUsername(token);
        String role = jwtUtill.getRole(token);

        User user = new User();
        user.setLoginId(username);
        user.setRole(role);

        CustomUserDetail customUserDetails = new CustomUserDetail(user);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
