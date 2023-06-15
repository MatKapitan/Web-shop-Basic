package com.matkap.webshopbasics.repository;

import com.matkap.webshopbasics.entity.Product;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  Optional<Product> findByIdAndAvailable(Long id, Boolean isAvailable);

  @Query(
      """
          select p from Product p 
            join p.orderItem 
          where p.orderItem.id = :orderId 
            and p.id = :id""")
  Optional<Product> findByIdAndOrderId(@Param("id") Long id, @Param("orderId") Long orderId);

}
