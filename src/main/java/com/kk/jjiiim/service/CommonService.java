package com.kk.jjiiim.service;


import com.kk.jjiiim.dto.CheckId;
import com.kk.jjiiim.dto.CheckPhoneNumber;
import com.kk.jjiiim.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Transactional
@Service
public class CommonService {

    private final UserRepository userRepository;


    public CheckId.Response isDuplicatedId(CheckId.Request dto) {
        return new CheckId.Response(
                userRepository.existsByLoginId(dto.getLoginId()));
    }

    public CheckPhoneNumber.Response isDuplicatedPhoneNumber(CheckPhoneNumber.Request dto) {
        return  new CheckPhoneNumber.Response(
                userRepository.existsByPhoneNumber(dto.getPhoneNumber()));
    }
}
