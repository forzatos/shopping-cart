package com.shoppingcart.order.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingcart.order.domain.Order;
import com.shoppingcart.order.dto.OrderDTO;
import com.shoppingcart.order.repository.OrderRepository;
import com.shoppingcart.order.util.OrderMapperUtil;

@Service
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;

	@Autowired
	public OrderServiceImpl(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@Override
	public List<OrderDTO> findByDateSearch(LocalDateTime startDate, LocalDateTime endDate) {
		List<Order> orders = new ArrayList<>();
		if (startDate == null && endDate == null) {
			orders = orderRepository.findAll();
		} else if (endDate == null) {
			orders = orderRepository.findByCreatedDateGreaterThanEqual(startDate);
		} else if (startDate == null) {
			orders = orderRepository.findByCreatedDateLessThanEqual(endDate);
		} else {
			orders = orderRepository.findByCreatedDateGreaterThanEqualAndCreatedDateLessThanEqual(startDate, endDate);
		}
		return OrderMapperUtil.getOrderDTOList(orders);
	}

	@Override
	public OrderDTO save(OrderDTO orderDTO) {
		Order order = OrderMapperUtil.getOrderEntity(orderDTO);
		return OrderMapperUtil.getOrderDTO(orderRepository.save(order));
	}

}
