package com.zosh.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zosh.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
