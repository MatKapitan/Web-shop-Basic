package com.matkap.Webshop_basics.repository;

import com.matkap.Webshop_basics.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByIdAndIsAvailable(Long id, Boolean isAvailable);

}
