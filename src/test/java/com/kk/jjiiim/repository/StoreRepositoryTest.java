package com.kk.jjiiim.repository;

import com.kk.jjiiim.domain.Category;
import com.kk.jjiiim.domain.Manager;
import com.kk.jjiiim.domain.Role;
import com.kk.jjiiim.domain.Store;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
class StoreRepositoryTest {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    EntityManager em;

    @BeforeEach
    void beforeEach(){

        Store s = Store.builder()
                .address("123")
                .category(Category.CHINESE)
                .latitude(37.001)
                .longitude(127.001)
                .name("가게")
                .description("1234")
//                .id(1L)
                .build();

        Manager m = Manager.builder()
//                .id(2L)
                .password("1234")
                .loginId("1234")
                .role(Role.ROLE_MANAGER)
                .phoneNumber("010")
                .build();

        em.persist(m);

        s.associatedWithManager(m);
        em.persist(s);



    }

    @Test
    void readMyStores () throws Exception{
        //given
        final long id = 1L;


        //when
//        List<MyStores.Response> list = storeRepository.getMyStores(id);
        storeRepository.getMyStores(id);

        //then

//        final String query = "select store.id as id, store.name as name, " +
//                "NULLIF() as rating ," +
//                "IF(IS NULL(count(review.id), 0, count(review.id)))as nOfReviews  from store " +
//                "left join reservation on reservation.store_id = store.id " +
//                "left join review on reservation.id = review.reservation_id " +
//                "group by store.id";
//        Query nativeQuery = em.createNativeQuery(query, MyStoresInf.class);
//
//        List resultList = nativeQuery.getResultList();
//
//        System.out.println(resultList);


    }
}