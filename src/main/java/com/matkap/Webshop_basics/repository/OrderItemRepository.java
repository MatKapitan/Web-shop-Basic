package com.matkap.Webshop_basics.repository;

import com.matkap.Webshop_basics.entity.OrderItem;
import com.matkap.Webshop_basics.entity.StatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    Page<OrderItem> getByOrderId(Long orderId, Pageable page);

    boolean existsByProduct_IdAndOrder_Id(Long product_id, Long order_id);

    Optional<OrderItem> findByProduct_IdAndOrder_Id(Long product_id, Long order_id);











}
