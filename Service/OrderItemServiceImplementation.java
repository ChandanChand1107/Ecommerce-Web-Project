package com.zosh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zosh.model.OrderItem;

@Service
public class OrderItemServiceImplementation implements OrderItemService{
	@Autowired
	private OrderItemRepository orderItemRepository;

	@Override
	public OrderItem createdOrderItem(OrderItem orderItem) {
		// TODO Auto-generated method stub
		return orderItemRepository.save(orderItem);
	}

}
