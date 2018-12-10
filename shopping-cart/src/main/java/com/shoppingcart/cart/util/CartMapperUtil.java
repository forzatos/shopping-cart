package com.shoppingcart.cart.util;

import java.util.ArrayList;
import java.util.List;

import com.shoppingcart.cart.domain.Cart;
import com.shoppingcart.cart.domain.CartProduct;
import com.shoppingcart.cart.dto.CartDTO;
import com.shoppingcart.cart.dto.CartProductDTO;

public class CartMapperUtil {

	public static CartDTO getCartDTO(Cart cart) {
		if (cart == null) {
			return null;
		}
		CartDTO cartDTO = new CartDTO();
		cartDTO.setId(cart.getId());
		cartDTO.setEmail(cart.getEmail());
		cartDTO.setCreatedDate(cart.getCreatedDate());
		cartDTO.setCartAmount(cart.getCartAmount());
		cartDTO.setOrderProducts(getCartProductDTOList(cart.getCartProducts()));
		return cartDTO;
	}

	public static CartProductDTO getCartProductDTO(CartProduct cartProduct) {
		if (cartProduct == null) {
			return null;
		}
		CartProductDTO cartProductDTO = new CartProductDTO();
		cartProductDTO.setId(cartProduct.getId());
		cartProductDTO.setName(cartProduct.getName());
		cartProductDTO.setPrice(cartProduct.getPrice());
		return cartProductDTO;
	}

	private static List<CartProductDTO> getCartProductDTOList(List<CartProduct> cartProducts) {
		List<CartProductDTO> cartProductDTOs = new ArrayList<>();
		if (cartProducts == null) {
			return cartProductDTOs;
		}
		cartProducts.forEach(cartProduct -> {
			cartProductDTOs.add(getCartProductDTO(cartProduct));
		});
		return cartProductDTOs;
	}

}
