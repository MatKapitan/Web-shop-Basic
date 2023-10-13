package com.matkap.Webshop_basics.repository;

import com.matkap.Webshop_basics.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByIdAndIsAvailable(Long id, Boolean isAvailable);

    @Query(
            """
                select p from Product p 
                  join p.orderItem 
                where p.orderItem.id = :orderId 
                  and p.id = :id""")
    Optional<Product> findByIdAndOrderId(@Param("id") Long id, @Param("orderId") Long orderId);

}
