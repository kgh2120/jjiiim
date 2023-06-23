package com.kk.jjiiim.service;

import com.kk.jjiiim.dto.CheckId;
import com.kk.jjiiim.dto.CheckPhoneNumber;
import com.kk.jjiiim.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;


import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class CommonServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CommonService commonService;


    @DisplayName("아이디 중복 검사 - [성공] 사용 가능")
    @Test
    void isDuplicatedId_SUCCESS_FALSE () throws Exception{
        //given
        CheckId.Request request = new CheckId.Request("foobar");
        given(userRepository.existsByLoginId(anyString()))
                .willReturn(false);
        //when

        CheckId.Response response = commonService.isDuplicatedId(request);

        //then
        assertThat(response.isResult())
                .isFalse();
    }
    @DisplayName("아이디 중복 검사 - [성공] 사용 불가")
    @Test
    void isDuplicatedId_SUCCESS_TRUE () throws Exception{
        //given
        CheckId.Request request = new CheckId.Request("foobar");
        given(userRepository.existsByLoginId(anyString()))
                .willReturn(true);
        //when

        CheckId.Response response = commonService.isDuplicatedId(request);

        //then
        assertThat(response.isResult())
                .isTrue();
    }

    @DisplayName("전화 번호 중복 검사 - [성공] 사용 가능")
    @Test
    void isDuplicatedPhoneNumber_SUCCESS_FALSE () throws Exception{
        //given
        CheckPhoneNumber.Request request = new CheckPhoneNumber.Request("010-0000-0000");
        given(userRepository.existsByPhoneNumber(anyString()))
                .willReturn(false);
        //when

        CheckPhoneNumber.Response response = commonService.isDuplicatedPhoneNumber(request);

        //then
        assertThat(response.isResult())
                .isFalse();
    }
    @DisplayName("전화 번호 중복 검사 - [성공] 사용 불가")
    @Test
    void isDuplicatedPhoneNumber_SUCCESS_TRUE () throws Exception{
        //given
        CheckPhoneNumber.Request request = new CheckPhoneNumber.Request("010-0000-0000");
        given(userRepository.existsByPhoneNumber(anyString()))
                .willReturn(true);
        //when

        CheckPhoneNumber.Response response = commonService.isDuplicatedPhoneNumber(request);

        //then
        assertThat(response.isResult())
                .isTrue();
    }

}