package com.shoppingcart.order.service;

import java.time.LocalDateTime;
import java.util.List;

import com.shoppingcart.order.dto.OrderDTO;

public interface OrderService {

	public List<OrderDTO> findByDateSearch(LocalDateTime startDate, LocalDateTime endDate);

	public OrderDTO save(OrderDTO orderDTO);

}
