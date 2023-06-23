package com.kk.jjiiim.repository;

import com.kk.jjiiim.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {


}
