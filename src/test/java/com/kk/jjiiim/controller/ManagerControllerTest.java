package com.kk.jjiiim.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kk.jjiiim.config.SecurityConfig;
import com.kk.jjiiim.domain.Category;
import com.kk.jjiiim.dto.MyStores;
import com.kk.jjiiim.dto.RegisterStore;
import com.kk.jjiiim.dto.SignUp;
import com.kk.jjiiim.exception.CommonApiException;
import com.kk.jjiiim.exception.ManagerApiException;
import com.kk.jjiiim.service.ManagerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {ManagerController.class},
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = SecurityConfig.class)},
        excludeAutoConfiguration = {SecurityAutoConfiguration.class,
        })
class ManagerControllerTest {


    public static final String REGISTER_STORE = "/manager/store";
    public static final String READ_MY_STORES = "/manager/store";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ManagerService managerService;

    @InjectMocks
    private  ManagerController managerController;
    private ObjectMapper objectMapper = new ObjectMapper();

    private static final String SIGN_UP = "/manager/signup";

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

        //when    //then
        mockMvc.perform(post(SIGN_UP)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @DisplayName("점장 회원 가입 - [실패] ID 중복")
    @Test
    void managerSignUp_FAIL_DUPLICATED_ID () throws Exception{
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
                .when(managerService)
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

    @DisplayName("점장 회원 가입 - [실패] 전화 번호 중복")
    @Test
    void managerSignUp_FAIL_DUPLICATED_PHONE () throws Exception{
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
                .when(managerService)
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
        CommonApiException occurredException = CommonApiException.passwordUnMatched();
        doThrow(occurredException)
                .when(managerService)
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

    @DisplayName("매장 등록 - [성공]")
    @Test
    void registerStore_SUCCESS () throws Exception{
        //given
        RegisterStore.Request request = RegisterStore.Request.builder()
                .name("김밥천국 강남점")
                .description("김밥이 싸고 맛좋아요")
                .address("서울특별시 서초구 강남대로 10")
                .category(Category.KOREAN)
                .latitude(37.482949)
                .longitude(127.005578)
                .build();

        //when then
        mockMvc.perform(post(REGISTER_STORE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
               )
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @DisplayName("매장 등록 - [실패] 매장 명 중복")
    @Test
    void registerStore_FAIL_DUPLICATED_STORE_NAME () throws Exception{
        //given
        RegisterStore.Request request = RegisterStore.Request.builder()
                .name("김밥천국 강남점")
                .description("김밥이 싸고 맛좋아요")
                .address("서울특별시 서초구 강남대로 10")
                .category(Category.KOREAN)
                .latitude(37.482949)
                .longitude(127.005578)
                .build();
        ManagerApiException occurredException = ManagerApiException.alreadyRegisteredStoreName();
        doThrow(occurredException)
                .when(managerService)
                        .registerStore(any());
        //when then
        mockMvc.perform(post(REGISTER_STORE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errorCode").value(occurredException.getErrorCode()))
                .andExpect(jsonPath("$.errorMessage").value(occurredException.getErrorMessage()))
                .andExpect(jsonPath("$.errorName").value(occurredException.getErrorName()));
    }

    @DisplayName("내 매장 조회 - [성공]")
    @Test
    void readMyStores_SUCCESS() throws Exception {
        //given
        List<MyStores> results = new ArrayList<>();
        for (int i = 0; i < 5; i++)
            results.add(new MyStores(Integer.toUnsignedLong(i),"가게"+i, 0.0, 0L));

        given(managerService.readMyStores())
                .willReturn(results);
        //when then
        mockMvc.perform(get(READ_MY_STORES)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(5))
                .andExpect(jsonPath("$[0].id").value(0))
                .andExpect(jsonPath("$[0].name").value("가게0"));
    }
}