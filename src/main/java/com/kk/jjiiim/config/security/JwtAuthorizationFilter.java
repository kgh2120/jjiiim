package com.kk.jjiiim.config.security;


import com.kk.jjiiim.service.CommonService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;
@Slf4j
@RequiredArgsConstructor
// 유저의 Header에 JWT가 있는지 확인하고, 해당 JWT가 유효한 값인지 체크
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final CommonService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // request에서 값을 가져온다
        String authorization = request.getHeader("Authorization");
        String token = null;
        // token 변환
        if (StringUtils.hasText(authorization) && authorization.contains("Bearer")) {
            token = authorization.replace("Bearer","");
        }
        // 토큰 검증
        if (token != null && jwtProvider.validateToken(token) != null) {
            String loginId = jwtProvider.validateToken(token).getSubject();
            SecurityContextHolder.getContext().setAuthentication(getAuthentication(loginId));

        }
        filterChain.doFilter(request,response);
    }
    private UsernamePasswordAuthenticationToken getAuthentication(String loginId) {
        UserDetails users = userService.loadUserByUsername(loginId);
        return new UsernamePasswordAuthenticationToken(users,users.getPassword(),users.getAuthorities());
    }

}
