package com.kk.jjiiim.util;

import com.kk.jjiiim.dto.SignUp;
import com.kk.jjiiim.exception.CommonApiException;
import com.kk.jjiiim.repository.UserRepository;

/**
 * 고객과 점장의 회원 가입 시 발생할 수 있는
 * 공통 검증을 처리하는 클래스이다.
 */
public class SignUpValidator {

    /**
     * 아래 3개의 검사를 진행한다.
     * 1. id 중복 검사
     * 2.전화 번호 중복 검사
     * 3. password match 검사
     * @param userRepository
     * @param dto
     */
    public static void validateSignUp(UserRepository userRepository, SignUp.Request dto){
        if (userRepository.existsByLoginId(dto.getLoginId()))
            throw CommonApiException.alreadyRegisteredLoginId();
        if(userRepository.existsByPhoneNumber(dto.getPhoneNumber()))
            throw CommonApiException.alreadyRegisteredPhoneNumber();
        if(!dto.getPassword().equals(dto.getPasswordRepeat()))
            throw CommonApiException.passwordUnMatched();
    }
}
