package com.shoppingcart.order.exception;

public class OrderNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -8995393760253914225L;

	public OrderNotFoundException(String exception) {
		super(exception);
	}
	
}
