package com.shoppingcart.cart.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingcart.cart.domain.Cart;
import com.shoppingcart.cart.domain.CartProduct;
import com.shoppingcart.cart.dto.CartDTO;
import com.shoppingcart.cart.repository.CartProductRepository;
import com.shoppingcart.cart.repository.CartRepository;
import com.shoppingcart.cart.util.CartMapperUtil;
import com.shoppingcart.common.util.ConstantValuesUtil;

@Service
public class CartServiceImpl implements CartService {

	private final CartRepository cartRepository;
	private final CartProductRepository cartProductRepository;
	private final CartAMPQService cartAMPQService;

	@Autowired
	public CartServiceImpl(CartRepository cartRepository, CartProductRepository cartProductRepository,
			CartAMPQService cartAMPQService) {
		this.cartRepository = cartRepository;
		this.cartProductRepository = cartProductRepository;
		this.cartAMPQService = cartAMPQService;
	}

	@Override
	public void triggerCheckout(UUID id, String email) {
		Cart cart = cartRepository.findByIdAndEmailIgnoreCase(id, email);
		if (cart == null) {
			return;
		}
		CartDTO cartDTO = CartMapperUtil.getCartDTO(cart);
		cartRepository.delete(cart);
		cartAMPQService.sendBrokerEvent(ConstantValuesUtil.EVENT_CHECKOUT, cartDTO);
	}

	@Override
	public CartDTO getCreatedCartForEmail(String email) {
		Cart cart = new Cart();
		cart.setCreatedDate(LocalDateTime.now());
		cart.setEmail(email);
		return CartMapperUtil.getCartDTO(cartRepository.save(cart));
	}

	@Override
	public CartDTO findCartByIdAndEmail(UUID id, String email) {
		Cart cart = cartRepository.findByIdAndEmailIgnoreCase(id, email);
		return CartMapperUtil.getCartDTO(cart);
	}

	@Override
	public CartDTO removeCartProduct(CartDTO cartDTO, UUID productId) {
		Cart cart = cartRepository.findById(cartDTO.getId()).orElse(null);
		if (cart == null) {
			return cartDTO;
		}
		if (cart.getCartProducts() == null || cart.getCartProducts().isEmpty()) {
			return cartDTO;
		}
		CartProduct cartProduct = cartProductRepository.findById(productId).orElse(null);
		if (cartProduct == null) {
			return cartDTO;
		}
		cart.setCartProducts(removeCartProductFromList(cart.getCartProducts(), cartProduct));
		return CartMapperUtil.getCartDTO(cartRepository.save(cart));
	}

	@Override
	public CartDTO addCartProduct(CartDTO cartDTO, UUID productId) {
		Cart cart = cartRepository.findById(cartDTO.getId()).orElse(null);
		if (cart == null) {
			return cartDTO;
		}
		CartProduct cartProduct = cartProductRepository.findById(productId).orElse(null);
		if (cartProduct == null) {
			return cartDTO;
		}
		if (cart.getCartProducts() == null) {
			cart.setCartProducts(new ArrayList<>());
		}
		cart.getCartProducts().add(cartProduct);
		return CartMapperUtil.getCartDTO(cartRepository.save(cart));
	}

	private List<CartProduct> removeCartProductFromList(List<CartProduct> cartProducts, CartProduct removeCartProduct) {
		boolean foundValue = false;
		List<CartProduct> filteredCartProducts = new ArrayList<>();
		for (CartProduct cartProduct : cartProducts) {
			if (!foundValue && cartProduct.getId().equals(removeCartProduct.getId())) {
				foundValue = true;
				continue;
			}
			filteredCartProducts.add(cartProduct);
		}
		return filteredCartProducts;
	}

}
