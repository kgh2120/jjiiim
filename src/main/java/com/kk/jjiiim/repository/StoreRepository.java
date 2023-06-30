package com.kk.jjiiim.repository;

import com.kk.jjiiim.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store,Long> {

    boolean existsByName(String name);
}
