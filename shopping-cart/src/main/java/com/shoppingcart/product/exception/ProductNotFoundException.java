package com.shoppingcart.product.exception;

public class ProductNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 8819184435512996864L;

	public ProductNotFoundException(String exception) {
		super(exception);
	}

}
