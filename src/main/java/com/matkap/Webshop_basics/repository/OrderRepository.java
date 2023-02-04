package com.matkap.Webshop_basics.repository;

import com.matkap.Webshop_basics.entity.Order;
import com.matkap.Webshop_basics.entity.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = """
            select sum(oi.quantity * p.price_eur)
            from webshop_order wo
            left join order_item oi on wo.id = oi.order_id
            left join product p on oi.product_id = p.id
            where oi.order_id = ?1
            """, nativeQuery = true)
    Optional<Double> getFinalPrice(Long id);

    boolean existsByCustomer_IdAndStatusEnum(Long id, StatusEnum statusEnum);














}
