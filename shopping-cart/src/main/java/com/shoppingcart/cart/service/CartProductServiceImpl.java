package com.shoppingcart.cart.service;

import java.util.Collections;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingcart.cart.domain.CartProduct;
import com.shoppingcart.cart.dto.CartProductDTO;
import com.shoppingcart.cart.repository.CartProductRepository;
import com.shoppingcart.cart.repository.CartRepository;
import com.shoppingcart.cart.util.CartMapperUtil;
import com.shoppingcart.common.util.ConstantValuesUtil;

@Service
public class CartProductServiceImpl implements CartProductService {

	private final CartProductRepository cartProductRepository;
	private final CartRepository cartRepository;

	@Autowired
	public CartProductServiceImpl(CartProductRepository cartProductRepository, CartRepository cartRepository) {
		this.cartProductRepository = cartProductRepository;
		this.cartRepository = cartRepository;
	}

	@Override
	public void save(CartProductDTO cartProductDTO) {
		saveOrUpdate(ConstantValuesUtil.EVENT_CREATED, cartProductDTO);
	}

	@Override
	public void update(CartProductDTO cartProductDTO) {
		saveOrUpdate(ConstantValuesUtil.EVENT_UPDATED, cartProductDTO);
	}

	@Override
	public void deleteById(UUID id) {
		CartProduct cartProduct = cartProductRepository.findById(id).orElse(null);
		if (cartProduct == null) {
			return;
		}
		if (cartProduct.getCarts() == null || cartProduct.getCarts().isEmpty()) {
			cartProductRepository.delete(cartProduct);
			return;
		}
		cartProduct.getCarts().forEach(cart -> {
			if (cart.getCartProducts() != null && !cart.getCartProducts().isEmpty()) {
				cart.getCartProducts().removeAll(Collections.singleton(cartProduct));
				cartRepository.save(cart);
			}
		});
	}

	private CartProductDTO saveOrUpdate(String brokerEvent, CartProductDTO cartProductDTO) {
		CartProduct cartProduct = cartProductRepository.findById(cartProductDTO.getId()).orElse(new CartProduct());
		cartProduct.setId(cartProductDTO.getId());
		cartProduct.setName(cartProductDTO.getName());
		cartProduct.setPrice(cartProductDTO.getPrice());
		return CartMapperUtil.getCartProductDTO(cartProductRepository.save(cartProduct));
	}
}
