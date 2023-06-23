package com.kk.jjiiim.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kk.jjiiim.config.SecurityConfig;
import com.kk.jjiiim.dto.SignUp;
import com.kk.jjiiim.exception.CommonApiException;
import com.kk.jjiiim.service.CustomerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static reactor.core.publisher.Mono.when;


@WebMvcTest(value = {CustomerController.class},
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = SecurityConfig.class)},
        excludeAutoConfiguration = {SecurityAutoConfiguration.class,
                SecurityFilterAutoConfiguration.class,
              }
)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CustomerService customerService;

    @InjectMocks
    private  CustomerController customerController;
    private ObjectMapper objectMapper = new ObjectMapper();

    private static final String SIGN_UP = "/customer/signup";

    @DisplayName("고객 회원 가입 - [성공]")
    @Test
    void customerSignUp_SUCCESS () throws Exception{
        //given
        SignUp.Request request = SignUp.Request.builder()
                .loginId("loginId")
                .name("foo")
                .password("password")
                .passwordRepeat("password")
                .phoneNumber("010-0000-0000")
                .build();

        //when    //then
        mockMvc.perform(post(SIGN_UP)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @DisplayName("고객 회원 가입 - [실패] ID 중복")
    @Test
    void customerSignUp_FAIL_DUPLICATED_ID () throws Exception{
        //given
        SignUp.Request request = SignUp.Request.builder()
                .loginId("loginId")
                .name("foo")
                .password("password")
                .passwordRepeat("password")
                .phoneNumber("010-0000-0000")
                .build();
        CommonApiException occurredException = CommonApiException.alreadyRegisteredLoginId();
        doThrow(occurredException)
                .when(customerService)
                        .signUp(any());
        //when    //then
        mockMvc.perform(post(SIGN_UP)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errorCode").value(occurredException.getErrorCode()))
                .andExpect(jsonPath("$.errorName").value(occurredException.getErrorName()))
                .andExpect(jsonPath("$.errorMessage").value(occurredException.getErrorMessage()))
                .andExpect(jsonPath("$.path").value(SIGN_UP));
    }

    @DisplayName("고객 회원 가입 - [실패] 전화 번호 중복")
    @Test
    void customerSignUp_FAIL_DUPLICATED_PHONE () throws Exception{
        //given
        SignUp.Request request = SignUp.Request.builder()
                .loginId("loginId")
                .name("foo")
                .password("password")
                .passwordRepeat("password")
                .phoneNumber("010-0000-0000")
                .build();
        CommonApiException occurredException = CommonApiException.alreadyRegisteredPhoneNumber();
        doThrow(occurredException)
                .when(customerService)
                .signUp(any());
        //when    //then
        mockMvc.perform(post(SIGN_UP)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errorCode").value(occurredException.getErrorCode()))
                .andExpect(jsonPath("$.errorName").value(occurredException.getErrorName()))
                .andExpect(jsonPath("$.errorMessage").value(occurredException.getErrorMessage()))
                .andExpect(jsonPath("$.path").value(SIGN_UP));
    }
    @DisplayName("고객 회원 가입 - [실패] 비밀 번호 불일치")
    @Test
    void customerSignUp_FAIL_PASSWORD_UN_MATCH () throws Exception{
        //given
        SignUp.Request request = SignUp.Request.builder()
                .loginId("loginId")
                .name("foo")
                .password("password")
                .passwordRepeat("password1")
                .phoneNumber("010-0000-0000")
                .build();
        CommonApiException occurredException = CommonApiException.passwordUnMatched();
        doThrow(occurredException)
                .when(customerService)
                .signUp(any());
        //when    //then
        mockMvc.perform(post(SIGN_UP)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value(occurredException.getErrorCode()))
                .andExpect(jsonPath("$.errorName").value(occurredException.getErrorName()))
                .andExpect(jsonPath("$.errorMessage").value(occurredException.getErrorMessage()))
                .andExpect(jsonPath("$.path").value(SIGN_UP));
    }

}