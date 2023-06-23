package com.kk.jjiiim.service;


import com.kk.jjiiim.dto.CheckId;
import com.kk.jjiiim.dto.CheckPhoneNumber;
import com.kk.jjiiim.exception.CommonApiException;
import com.kk.jjiiim.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Slf4j

@RequiredArgsConstructor
@Transactional
@Service
public class CommonService implements UserDetailsService {

    private final UserRepository userRepository;
    private final StringRedisTemplate redisTemplate;

    @Value("${jwt.expiration.refresh}")
    private long refreshTokenExpirationTime;



    public CheckId.Response isDuplicatedId(CheckId.Request dto) {
        return new CheckId.Response(
                userRepository.existsByLoginId(dto.getLoginId()));
    }

    public CheckPhoneNumber.Response isDuplicatedPhoneNumber(CheckPhoneNumber.Request dto) {
        return  new CheckPhoneNumber.Response(
                userRepository.existsByPhoneNumber(dto.getPhoneNumber()));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLoginId(username)
                .orElseThrow(CommonApiException::loginIdNotFound);
    }

    public void saveRefreshToken(String username, String refreshToken) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(username,refreshToken, Duration.ofMillis(refreshTokenExpirationTime));
    }
}
