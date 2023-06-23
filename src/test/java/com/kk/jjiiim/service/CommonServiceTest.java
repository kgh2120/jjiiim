package com.kk.jjiiim.service;

import com.kk.jjiiim.domain.Customer;
import com.kk.jjiiim.dto.CheckId;
import com.kk.jjiiim.dto.CheckPhoneNumber;
import com.kk.jjiiim.exception.CommonApiException;
import com.kk.jjiiim.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class CommonServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private StringRedisTemplate stringRedisTemplate;

    @Mock
    private ValueOperations ops;

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

    @DisplayName("아이디로 조회하기 - [성공]")
    @Test
    void loadByUsername_SUCCESS () throws Exception{
        final String loginId = "foobar";
        final String password = "foobar";
        Customer customer = Customer.builder()
                .loginId(loginId)
                .password(password)
                .build();
        //given
        given(userRepository.findByLoginId(anyString()))
                .willReturn(Optional.of(customer));
        //when
        UserDetails finded = commonService.loadUserByUsername(loginId);
        //then
        assertThat(finded.getUsername()).isEqualTo(loginId);
        assertThat(finded.getPassword()).isEqualTo(password);
    }
    @DisplayName("아이디로 조회하기 - [실패] 아이디가 존재하지 않습니다.")
    @Test
    void loadByUsername_FAIL_ID_NOT_FOUND () throws Exception{
        final String loginId = "foobar";
        CommonApiException occurredException = CommonApiException.loginIdNotFound();
        //given
        given(userRepository.findByLoginId(anyString()))
                .willReturn(Optional.empty());
        //when
        assertThatThrownBy(() -> commonService.loadUserByUsername(loginId))
                .isInstanceOf(CommonApiException.class)
                .hasFieldOrPropertyWithValue("errorName",occurredException.getErrorName())
                .hasFieldOrPropertyWithValue("errorMessage",occurredException.getErrorMessage());
    }


    @DisplayName("리프래시 토큰 저장 - [성공]")
    @Test
    void saveRefreshToken_SUCCESS () throws Exception{
        //given
        final String username = "username";
        final String refreshToken = "refreshToken";


        given(stringRedisTemplate.opsForValue())
                .willReturn(ops);

        //when //then
        assertThatNoException()
                .isThrownBy(() -> commonService.saveRefreshToken(username,refreshToken));



    }

}