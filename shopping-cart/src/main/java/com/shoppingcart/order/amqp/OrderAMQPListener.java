package com.shoppingcart.order.amqp;

import java.util.ArrayList;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shoppingcart.cart.dto.CartDTO;
import com.shoppingcart.common.util.ConstantValuesUtil;
import com.shoppingcart.order.dto.OrderDTO;
import com.shoppingcart.order.dto.OrderProductDTO;
import com.shoppingcart.order.service.OrderService;

@Component
public class OrderAMQPListener {

	private final OrderService orderService;

	@Autowired
	public OrderAMQPListener(OrderService orderService) {
		this.orderService = orderService;
	}

	@RabbitListener(queues = ConstantValuesUtil.QUEUE_CART_CHECKOUT)
	public void processOrderCreatedEvent(CartDTO cartDTO) {
		OrderDTO orderDTO = getOrderDTO(cartDTO);
		orderService.save(orderDTO);
	}

	private OrderDTO getOrderDTO(CartDTO cartDTO) {
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setCreatedDate(cartDTO.getCreatedDate());
		orderDTO.setEmail(cartDTO.getEmail());
		orderDTO.setCartId(cartDTO.getId());
		orderDTO.setOrderAmount(cartDTO.getCartAmount());
		orderDTO.setOrderProducts(new ArrayList<>());
		if (cartDTO.getOrderProducts() != null && !cartDTO.getOrderProducts().isEmpty()) {
			cartDTO.getOrderProducts().forEach(cartProductDTO -> {
				OrderProductDTO orderProductDTO = new OrderProductDTO();
				orderProductDTO.setName(cartProductDTO.getName());
				orderProductDTO.setPrice(cartProductDTO.getPrice());
				orderProductDTO.setProductId(cartProductDTO.getId());
				orderDTO.getOrderProducts().add(orderProductDTO);
			});
		}
		return orderDTO;
	}

}
