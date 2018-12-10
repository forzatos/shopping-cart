package com.shoppingcart.cart.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shoppingcart.common.util.ConstantValuesUtil;

@Configuration
public class CartAMQPConfiguration {

	@Bean
	public Queue cartCheckoutQueue() {
		return new Queue(ConstantValuesUtil.QUEUE_CART_CHECKOUT);
	}

	@Bean
	public TopicExchange cartsTopicExchange() {
		return new TopicExchange(ConstantValuesUtil.EXCHANGE_CARTS);
	}

	@Bean
	public Binding cartsCreatedBinding(Queue cartCheckoutQueue, TopicExchange cartsTopicExchange) {
		return BindingBuilder.bind(cartCheckoutQueue).to(cartsTopicExchange).with(ConstantValuesUtil.EVENT_CHECKOUT);
	}

}
