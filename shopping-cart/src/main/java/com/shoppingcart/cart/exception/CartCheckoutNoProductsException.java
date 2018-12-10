package com.shoppingcart.cart.exception;

public class CartCheckoutNoProductsException extends RuntimeException {

	private static final long serialVersionUID = 187286428236155882L;

	public CartCheckoutNoProductsException(String exception) {
		super(exception);
	}

}
