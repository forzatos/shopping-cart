package com.shoppingcart.cart.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingcart.cart.dto.CartDTO;
import com.shoppingcart.common.util.ConstantValuesUtil;

@Service
public class CartAMQPServiceImpl implements CartAMPQService {

	private final RabbitTemplate rabbitTemplate;

	@Autowired
	public CartAMQPServiceImpl(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	@Override
	public void sendBrokerEvent(String event, CartDTO cartDTO) {
		rabbitTemplate.convertAndSend(ConstantValuesUtil.EXCHANGE_CARTS, event, cartDTO);
	}

}
