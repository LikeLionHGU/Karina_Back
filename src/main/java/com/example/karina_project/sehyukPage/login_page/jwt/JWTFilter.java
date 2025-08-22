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
    private static final String[] WHITELIST = {
            "/", "/index", "/public/**", "/user/**",
            "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html"
    };
    private final org.springframework.util.AntPathMatcher matcher = new org.springframework.util.AntPathMatcher();

    private boolean isWhitelisted(String path) {
        for (String p : WHITELIST) {
            if (matcher.match(p, path)) return true;
        }
        return false;
    }



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1) 프리플라이트(OPTIONS)는 무조건 통과
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2) Swagger/공개 경로는 무조건 통과
        if (isWhitelisted(request.getServletPath())) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
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
