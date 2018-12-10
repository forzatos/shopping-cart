package com.shoppingcart.cart.service;

import java.util.UUID;

import com.shoppingcart.cart.dto.CartDTO;

public interface CartService {

	public CartDTO findCartByIdAndEmail(UUID id, String email);

	public CartDTO getCreatedCartForEmail(String email);

	public void triggerCheckout(UUID id, String email);

	public CartDTO removeCartProduct(CartDTO cartDTO, UUID productId);

	public CartDTO addCartProduct(CartDTO cartDTO, UUID productId);

}
