package com.matkap.webshopbasics.repository;

import com.matkap.webshopbasics.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Transactional
    @Modifying
    @Query("update Customer c set c.first_name = ?1, c.last_name = ?2, c.email = ?3 where c.id = ?4")
    int updateFirst_nameAndLast_nameAndEmailById(String first_name, String last_name, String email, Long id);



}
