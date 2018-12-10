package com.shoppingcart.cart.service;

import java.util.UUID;

import com.shoppingcart.cart.dto.CartProductDTO;

public interface CartProductService {

	public void save(CartProductDTO cartProductDTO);

	public void update(CartProductDTO cartProductDTO);

	public void deleteById(UUID id);

}
