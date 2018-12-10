package com.shoppingcart.cart.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcart.cart.dto.CartDTO;
import com.shoppingcart.cart.exception.CartCheckoutNoProductsException;
import com.shoppingcart.cart.exception.CartNotFoundException;
import com.shoppingcart.cart.service.CartService;
import com.shoppingcart.common.exception.CustomMethodArgumentNotValidException;

@RestController
@RequestMapping("/carts")
public class CartController {

	private final CartService cartService;

	@Autowired
	public CartController(CartService cartService) {
		this.cartService = cartService;
	}

	@GetMapping
	public ResponseEntity<CartDTO> createCartForEmail(
			@RequestHeader(value = "user-email", required = true) String userEmail) {
		if (userEmail == null || userEmail.trim().length() == 0) {
			throw new CustomMethodArgumentNotValidException("user email cannot be null or empty.");
		}
		CartDTO cartDTO = cartService.getCreatedCartForEmail(userEmail);
		return ResponseEntity.ok(cartDTO);
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<CartDTO> findCartByEmailAndId(
			@RequestHeader(value = "user-email", required = true) String userEmail,
			@PathVariable(name = "id", required = true) UUID id) {
		if (userEmail == null || userEmail.trim().length() == 0) {
			throw new CustomMethodArgumentNotValidException("user email cannot be null or empty.");
		}
		CartDTO cartDTO = cartService.findCartByIdAndEmail(id, userEmail);
		if (cartDTO == null) {
			throw new CartNotFoundException("cart not found for given parameters.");
		}
		return ResponseEntity.ok(cartDTO);
	}

	@GetMapping(path = "/checkout/{id}")
	public ResponseEntity<?> triggerCheckout(
			@RequestHeader(value = "user-email", required = true) String userEmail,
			@PathVariable(name = "id", required = true) UUID id) {
		if (userEmail == null || userEmail.trim().length() == 0) {
			throw new CustomMethodArgumentNotValidException("user email cannot be null or empty.");
		}
		CartDTO cartDTO = cartService.findCartByIdAndEmail(id, userEmail);
		if (cartDTO == null) {
			throw new CartNotFoundException("cart not found for given parameters.");
		}
		if (cartDTO.getOrderProducts() == null || cartDTO.getOrderProducts().isEmpty()) {
			throw new CartCheckoutNoProductsException("cart has no products, cannot checkout.");
		}
		cartService.triggerCheckout(id, userEmail);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@GetMapping(path = "/{id}/add/{productId}")
	public ResponseEntity<CartDTO> addProductToCart(
			@RequestHeader(value = "user-email", required = true) String userEmail,
			@PathVariable(name = "id", required = true) UUID id,
			@PathVariable(name = "productId", required = true) UUID productId) {
		if (userEmail == null || userEmail.trim().length() == 0) {
			throw new CustomMethodArgumentNotValidException("user email cannot be null or empty.");
		}
		CartDTO cartDTO = cartService.findCartByIdAndEmail(id, userEmail);
		if (cartDTO == null) {
			throw new CartNotFoundException("cart not found for given parameters.");
		}
		return ResponseEntity.ok(cartService.addCartProduct(cartDTO, productId));
	}

	@GetMapping(path = "/{id}/remove/{productId}")
	public ResponseEntity<CartDTO> removeProductFromCart(
			@RequestHeader(value = "user-email", required = true) String userEmail,
			@PathVariable(name = "id", required = true) UUID id,
			@PathVariable(name = "productId", required = true) UUID productId) {
		if (userEmail == null || userEmail.trim().length() == 0) {
			throw new CustomMethodArgumentNotValidException("user email cannot be null or empty.");
		}
		CartDTO cartDTO = cartService.findCartByIdAndEmail(id, userEmail);
		if (cartDTO == null) {
			throw new CartNotFoundException("cart not found for given parameters.");
		}
		return ResponseEntity.ok(cartService.removeCartProduct(cartDTO, productId));
	}

}
