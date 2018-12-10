package com.shoppingcart.product.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingcart.common.util.ConstantValuesUtil;
import com.shoppingcart.product.dto.ProductDTO;

@Service
public class ProductAMQPServiceImpl implements ProductAMPQService {

	private final RabbitTemplate rabbitTemplate;

	@Autowired
	public ProductAMQPServiceImpl(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	@Override
	public void sendBrokerEvent(String event, ProductDTO productDTO) {
		rabbitTemplate.convertAndSend(ConstantValuesUtil.EXCHANGE_PRODUCTS, event, productDTO);
	}

}
