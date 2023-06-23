package com.kk.jjiiim.service;

import com.kk.jjiiim.dto.SignUp;
import com.kk.jjiiim.exception.CommonApiException;
import com.kk.jjiiim.repository.CustomerRepository;
import com.kk.jjiiim.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static com.kk.jjiiim.exception.CommonErrorCode.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CustomerService customerService;

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
        given(userRepository.existsByLoginId(any()))
                .willReturn(false);
        given(userRepository.existsByPhoneNumber(any()))
                .willReturn(false);
        //when //then
        assertThatNoException()
                .isThrownBy(() -> customerService.signUp(request));
    }

    @DisplayName("고객 회원 가입 - [실패] ID 중복")
    @Test
    void customerSignUp_FAIL_DUPLICATE_ID () throws Exception{
        //given
        SignUp.Request request = SignUp.Request.builder()
                .loginId("loginId")
                .name("foo")
                .password("password")
                .passwordRepeat("password")
                .phoneNumber("010-0000-0000")
                .build();
        given(userRepository.existsByLoginId(any()))
                .willReturn(true);
        //when //then
        assertThatThrownBy(() -> customerService.signUp(request))
                .isInstanceOf(CommonApiException.class)
                .hasFieldOrPropertyWithValue("errorName", ALREADY_REGISTERED_LOGIN_ID.getErrorName());
    }
    @DisplayName("고객 회원 가입 - [실패] 전화 번호 중복")
    @Test
    void customerSignUp_FAIL_DUPLICATE_PHONE () throws Exception{
        //given
        SignUp.Request request = SignUp.Request.builder()
                .loginId("loginId")
                .name("foo")
                .password("password")
                .passwordRepeat("password")
                .phoneNumber("010-0000-0000")
                .build();
        given(userRepository.existsByLoginId(any()))
                .willReturn(false);
        given(userRepository.existsByPhoneNumber(any()))
                .willReturn(true);
        //when //then
        assertThatThrownBy(() -> customerService.signUp(request))
                .isInstanceOf(CommonApiException.class)
                .hasFieldOrPropertyWithValue("errorName", ALREADY_REGISTERED_PHONE_NUMBER.getErrorName());
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
        given(userRepository.existsByLoginId(any()))
                .willReturn(false);
        given(userRepository.existsByPhoneNumber(any()))
                .willReturn(false);
        //when //then
        assertThatThrownBy(() -> customerService.signUp(request))
                .isInstanceOf(CommonApiException.class)
                .hasFieldOrPropertyWithValue("errorName", PASSWORD_UN_MATCHED.getErrorName());
    }

}