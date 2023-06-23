package com.kk.jjiiim.repository;

import com.kk.jjiiim.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByLoginId(String loginId);

    Optional<User> findByLoginId(String loginId);
}
