package com.kk.jjiiim.repository;

import com.kk.jjiiim.domain.Store;
import com.kk.jjiiim.dto.MyStores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store,Long> {

    boolean existsByName(String name);

    @Query("SELECT new com.kk.jjiiim.dto.MyStores(s.id, s.name, COALESCE(avg(rv.score),0), COALESCE(count(rv.id),0) ) " +
            "FROM Store s " +
            "LEFT JOIN FETCH Reservation r on r.store = s " +
            "LEFT JOIN FETCH Review  rv on rv.reservation = r " +
            "WHERE s.manager.id =:id " +
            "GROUP BY s.id"
    )
    List<MyStores> getMyStores(Long id);
}
