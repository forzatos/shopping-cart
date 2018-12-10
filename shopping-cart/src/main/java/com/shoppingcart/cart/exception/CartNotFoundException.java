package com.shoppingcart.cart.exception;

public class CartNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 7490820816781211218L;

	public CartNotFoundException(String exception) {
		super(exception);
	}

}
