package com.shoppingcart.order.exception.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.shoppingcart.common.exception.handler.ApplicationRestExceptionHandler;
import com.shoppingcart.order.exception.OrderNotFoundException;

@RestControllerAdvice
public class OrderRestExceptionHandler extends ApplicationRestExceptionHandler {

	@ExceptionHandler({ OrderNotFoundException.class })
	public ResponseEntity<Object> handleOrderNotFound(OrderNotFoundException ex, WebRequest request) {
		List<String> errorMessages = new ArrayList<>();
		errorMessages.add(ex.getMessage());
		String logMessage = request.getDescription(false) + "; " + ex.getLocalizedMessage();
		return getErrorResponseEntity(HttpStatus.NOT_FOUND, errorMessages, logMessage);
	}

}