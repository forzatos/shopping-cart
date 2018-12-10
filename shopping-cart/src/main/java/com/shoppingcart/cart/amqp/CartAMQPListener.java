package com.shoppingcart.cart.amqp;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shoppingcart.cart.dto.CartProductDTO;
import com.shoppingcart.cart.service.CartProductService;
import com.shoppingcart.common.util.ConstantValuesUtil;
import com.shoppingcart.product.dto.ProductDTO;

@Component
public class CartAMQPListener {

	private final CartProductService cartProductService;

	@Autowired
	public CartAMQPListener(CartProductService cartProductService) {
		this.cartProductService = cartProductService;
	}

	@RabbitListener(queues = ConstantValuesUtil.QUEUE_PRODUCT_CREATED)
	public void processProductCreatedEvent(ProductDTO productDTO) {
		CartProductDTO cartProductDTO = getCartProductDTO(productDTO);
		cartProductService.save(cartProductDTO);
	}

	@RabbitListener(queues = ConstantValuesUtil.QUEUE_PRODUCT_UPDATED)
	public void processProductUpdatedEvent(ProductDTO productDTO) {
		CartProductDTO cartProductDTO = getCartProductDTO(productDTO);
		cartProductService.update(cartProductDTO);
	}

	@RabbitListener(queues = ConstantValuesUtil.QUEUE_PRODUCT_DELETED)
	public void processProductDeletedEvent(ProductDTO productDTO) {
		cartProductService.deleteById(productDTO.getId());
	}

	private CartProductDTO getCartProductDTO(ProductDTO productDTO) {
		CartProductDTO cartProductDTO = new CartProductDTO();
		cartProductDTO.setId(productDTO.getId());
		cartProductDTO.setName(productDTO.getName());
		cartProductDTO.setPrice(productDTO.getPrice());
		return cartProductDTO;
	}

}
