package com.kk.jjiiim.repository;

import com.kk.jjiiim.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByLoginId(String loginId);
}
