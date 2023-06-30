package com.kk.jjiiim.service;

import com.kk.jjiiim.domain.Category;
import com.kk.jjiiim.domain.Manager;
import com.kk.jjiiim.dto.RegisterStore;
import com.kk.jjiiim.dto.SignUp;
import com.kk.jjiiim.exception.CommonApiException;
import com.kk.jjiiim.exception.ManagerApiException;
import com.kk.jjiiim.exception.ManagerErrorCode;
import com.kk.jjiiim.repository.ManagerRepository;
import com.kk.jjiiim.repository.StoreRepository;
import com.kk.jjiiim.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static com.kk.jjiiim.exception.CommonErrorCode.*;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ManagerServiceTest {


    @Mock
    private ManagerRepository managerRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private StoreRepository storeRepository;
    @Mock
    private SecurityContextHolder securityContextHolder;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;


    @InjectMocks
    private ManagerService managerService;

    @DisplayName("점장 회원 가입 - [성공]")
    @Test
    void managerSignUp_SUCCESS () throws Exception{
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
//        given(passwordEncoder.encode(anyString()))
//                .willReturn(UUID.randomUUID().toString());
        //when //then
        assertThatNoException()
                .isThrownBy(() -> managerService.signUp(request));
    }

    @DisplayName("점장 회원 가입 - [실패] ID 중복")
    @Test
    void managerSignUp_FAIL_DUPLICATE_ID () throws Exception{
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
        assertThatThrownBy(() -> managerService.signUp(request))
                .isInstanceOf(CommonApiException.class)
                .hasFieldOrPropertyWithValue("errorName", ALREADY_REGISTERED_LOGIN_ID.getErrorName());
    }
    @DisplayName("점장 회원 가입 - [실패] 전화 번호 중복")
    @Test
    void managerSignUp_FAIL_DUPLICATE_PHONE () throws Exception{
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
        assertThatThrownBy(() -> managerService.signUp(request))
                .isInstanceOf(CommonApiException.class)
                .hasFieldOrPropertyWithValue("errorName", ALREADY_REGISTERED_PHONE_NUMBER.getErrorName());
    }

    @DisplayName("점장 회원 가입 - [실패] 비밀 번호 불일치")
    @Test
    void managerSignUp_FAIL_PASSWORD_UN_MATCH () throws Exception{
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
        assertThatThrownBy(() -> managerService.signUp(request))
                .isInstanceOf(CommonApiException.class)
                .hasFieldOrPropertyWithValue("errorName", PASSWORD_UN_MATCHED.getErrorName());
    }

    @DisplayName("매장 등록 - [성공]")
    @Test
    void registerStore_SUCCESS  () throws Exception{
        //given

        RegisterStore.Request request = RegisterStore.Request.builder()
                .name("김밥천국 강남점")
                .description("김밥이 싸고 맛좋아요")
                .address("서울특별시 서초구 강남대로 10")
                .category(Category.KOREAN)
                .latitude(37.482949)
                .longitude(127.005578)
                .build();

        Manager manager = Manager.builder()
                .build();
        given(securityContext.getAuthentication())
                .willReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        given(SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .willReturn(manager);
        given(storeRepository.existsByName(anyString()))
                .willReturn(false);
        given(managerRepository.findById(any()))
                .willReturn(Optional.of(manager));
        //when
        //then
        assertThatNoException()
                .isThrownBy(() -> managerService.registerStore(request));
    }

    @DisplayName("매장 등록 - [실패] 매장 명 중복")
    @Test
    void registerStore_FAIL_DUPLICATED_STORE_NAME  () throws Exception{
        //given

        RegisterStore.Request request = RegisterStore.Request.builder()
                .name("김밥천국 강남점")
                .description("김밥이 싸고 맛좋아요")
                .address("서울특별시 서초구 강남대로 10")
                .category(Category.KOREAN)
                .latitude(37.482949)
                .longitude(127.005578)
                .build();


        given(storeRepository.existsByName(anyString()))
                .willReturn(true);

        //when
        //then
        assertThatThrownBy(() -> managerService.registerStore(request))
                .isInstanceOf(ManagerApiException.class)
                .hasFieldOrPropertyWithValue("errorName", ManagerErrorCode.ALREADY_REGISTERED_STORE_NAME.getErrorName());
    }
}