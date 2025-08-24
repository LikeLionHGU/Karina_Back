

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

    private final JWTUtil jwtUtill;
    private final org.springframework.util.AntPathMatcher matcher = new org.springframework.util.AntPathMatcher();
    private static final String[] WHITELIST = {
            "/", "/index", "/public/**", "/user/**",
            "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html"
    };

    private boolean isWhitelisted(String path) {
        for (String p : WHITELIST) {
            if (matcher.match(p, path)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. OPTIONS 요청은 무조건 통과 (CORS preflight)
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. 화이트리스트 경로도 무조건 통과
        if (isWhitelisted(request.getServletPath())) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Authorization 헤더가 없거나 Bearer 토큰 형식이 아니면 다음 필터로
        String authorization = request.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 4. JWT 토큰 추출 및 유효성 검사
        String token = authorization.substring(7); // "Bearer ".length() == 7

        // 토큰 만료 검사
        if (jwtUtill.isExpired(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unathorized
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"code\": \"TOKEN_EXPIRED\"}");
            return;
        }

        // 5. 토큰에서 사용자 정보 추출 (클레임)
        String username = jwtUtill.getUsername(token);
        String role = jwtUtill.getRole(token);
        Long userId = jwtUtill.getUserId(token);

        // 6. ROLE_ 접두사 보정 (JWTUtil에서 접두사를 붙여주면 생략 가능)
        if (role != null && !role.startsWith("ROLE_")) {
            role = "ROLE_" + role;
        }

        // 7. SecurityContext에 인증 정보 설정
        User user = new User();
        user.setId(userId);
        user.setLoginId(username);
        user.setRole(role);

        CustomUserDetail principal = new CustomUserDetail(user);

        // JWT는 비밀번호가 없으므로 두 번째 인자는 null로 설정
        Authentication authToken = new UsernamePasswordAuthenticationToken(
                principal, null, principal.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);

        // 8. 다음 필터로 진행
        filterChain.doFilter(request, response);
    }
}