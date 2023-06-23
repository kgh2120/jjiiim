package com.kk.jjiiim.config.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;


@Slf4j
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response
            , AuthenticationException exception) throws IOException, ServletException {
        log.error("[CustomAuthenticationFailureHandler] - 필터 예외 처리");
        log.error("cause : {}", exception);
          /*
        BadCredentialsException : 아이디는 맞는데 비번을 틀렸을 때; -> JwtAuthenticationFilter에서 password를 credential 항목에 넣었음.
        CannotCreateTransactionException : DB와의 연결이 끊겨 Jpa 트랜잭션을 만들지 못할 때 발생
        UserIdNotFoundException : ID가 틀렸을 때
         */
//        ErrorResponse errorResponse = null;
//        if (exception instanceof BadCredentialsException) {
//            errorResponse = ErrorResponse.createErrorResponse(LOGIN_DATA_NOT_FOUND,request.getRequestURI());
//        }else{
//            switch (exception.getCause().getClass().getSimpleName()){
//                case "CannotCreateTransactionException" :
//                    errorResponse = ErrorResponse.createErrorResponse(DB_CONNECT_FAIL,request.getRequestURI());
//                    break;
//                case "LoginDataNotFoundException" :
//                    errorResponse = ErrorResponse.createErrorResponse(LOGIN_DATA_NOT_FOUND,request.getRequestURI());
//                    break;
//                case "BlockUserLoginException" :
//                    errorResponse = ErrorResponse.createErrorResponse((BlockUserLoginException)exception.getCause(),request.getRequestURI());
//                    break;
//            }
//        }
//        if (errorResponse != null) {
//            ObjectMapper objectMapper = new ObjectMapper();
//            response.setCharacterEncoding("UTF-8");
//            response.setContentType(String.valueOf(MediaType.APPLICATION_JSON));
//            response.setStatus(errorResponse.getHttpStatus().value());
//            response.getWriter().println(objectMapper.writeValueAsString(errorResponse));
//        }
    }
}
