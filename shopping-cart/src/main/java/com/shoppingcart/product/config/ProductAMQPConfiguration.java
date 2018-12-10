package com.shoppingcart.product.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shoppingcart.common.util.ConstantValuesUtil;

@Configuration
public class ProductAMQPConfiguration {

	@Bean
	public Queue productCreatedQueue() {
		return new Queue(ConstantValuesUtil.QUEUE_PRODUCT_CREATED);
	}

	@Bean
	public Queue productUpdatedQueue() {
		return new Queue(ConstantValuesUtil.QUEUE_PRODUCT_UPDATED);
	}

	@Bean
	public Queue productDeletedQueue() {
		return new Queue(ConstantValuesUtil.QUEUE_PRODUCT_DELETED);
	}

	@Bean
	public TopicExchange productsTopicExchange() {
		return new TopicExchange(ConstantValuesUtil.EXCHANGE_PRODUCTS);
	}

	@Bean
	public Binding productsCreatedBinding(Queue productCreatedQueue, TopicExchange productsTopicExchange) {
		return BindingBuilder.bind(productCreatedQueue).to(productsTopicExchange)
				.with(ConstantValuesUtil.EVENT_CREATED);
	}

	@Bean
	public Binding productsUpdatedBinding(Queue productUpdatedQueue, TopicExchange productsTopicExchange) {
		return BindingBuilder.bind(productUpdatedQueue).to(productsTopicExchange)
				.with(ConstantValuesUtil.EVENT_UPDATED);
	}

	@Bean
	public Binding productsDeletedBinding(Queue productDeletedQueue, TopicExchange productsTopicExchange) {
		return BindingBuilder.bind(productDeletedQueue).to(productsTopicExchange)
				.with(ConstantValuesUtil.EVENT_DELETED);
	}

}