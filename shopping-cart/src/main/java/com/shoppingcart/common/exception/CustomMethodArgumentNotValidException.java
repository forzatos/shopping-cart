package com.shoppingcart.common.exception;

public class CustomMethodArgumentNotValidException extends RuntimeException {

	private static final long serialVersionUID = 180983575646209655L;

	public CustomMethodArgumentNotValidException(String exception) {
		super(exception);
	}
}
