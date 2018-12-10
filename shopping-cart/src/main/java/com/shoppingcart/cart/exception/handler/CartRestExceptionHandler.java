package com.shoppingcart.cart.exception.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.shoppingcart.cart.exception.CartCheckoutNoProductsException;
import com.shoppingcart.cart.exception.CartNotFoundException;
import com.shoppingcart.common.exception.handler.ApplicationRestExceptionHandler;

@RestControllerAdvice
public class CartRestExceptionHandler extends ApplicationRestExceptionHandler {

	@ExceptionHandler({ CartNotFoundException.class })
	public ResponseEntity<Object> handleCartNotFound(CartNotFoundException ex, WebRequest request) {
		List<String> errorMessages = new ArrayList<>();
		errorMessages.add(ex.getMessage());
		String logMessage = request.getDescription(false) + "; " + ex.getLocalizedMessage();
		return getErrorResponseEntity(HttpStatus.NOT_FOUND, errorMessages, logMessage);
	}

	@ExceptionHandler({ CartCheckoutNoProductsException.class })
	public ResponseEntity<Object> handleCartCheckoutNoProductsException(CartCheckoutNoProductsException ex,
			WebRequest request) {
		List<String> errorMessages = new ArrayList<>();
		errorMessages.add(ex.getMessage());
		String logMessage = request.getDescription(false) + "; " + ex.getLocalizedMessage();
		return getErrorResponseEntity(HttpStatus.NOT_FOUND, errorMessages, logMessage);
	}

}