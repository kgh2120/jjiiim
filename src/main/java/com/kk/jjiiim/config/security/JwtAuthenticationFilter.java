package com.kk.jjiiim.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kk.jjiiim.domain.User;
import com.kk.jjiiim.dto.LogIn;
import com.kk.jjiiim.service.CommonService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
@Slf4j @RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final CommonService commonService;
    private final AuthenticationFailureHandler authenticationFailureHandler;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        ObjectMapper mapper = new ObjectMapper();
        LogIn.Request dto = null;
        try {
            dto = mapper.readValue(request.getInputStream(),LogIn.Request.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        if(!LoginValidator.validateLogin(dto.getLoginId(), dto.getPassword())){
//            throw new LoginValidateErrorException();
//        }


        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(dto.getLoginId(), dto.getPassword());
        return authenticationManager.authenticate(authenticationToken);

    }

    // jwt 토큰을 만들어서 response로 전달해줘서 유저가 받아서 사용할 수 있게끔 한다.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();
        String accessToken = jwtProvider.createAccessToken(user.getUsername());
        String refreshToken = jwtProvider.createRefreshToken(user.getUsername());

        commonService.saveRefreshToken(user.getUsername(), refreshToken);

        response.addHeader("access-token",accessToken);
        response.addHeader("refresh-token",refreshToken);
        response.setStatus(200);

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.error("[JwtAuthenticationFilter] - 인증실패");
        authenticationFailureHandler.onAuthenticationFailure(request,response,failed);
    }
}
