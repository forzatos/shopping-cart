package com.shoppingcart.common.exception.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.shoppingcart.common.dto.ErrorRestResponseDTO;
import com.shoppingcart.common.exception.CustomMethodArgumentNotValidException;

@RestControllerAdvice
public abstract class ApplicationRestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
			WebRequest request) {
		List<String> errorMessages = new ArrayList<>();
		errorMessages.add(ex.getName() + " must be of type " + ex.getRequiredType().getName());
		String logMessage = request.getDescription(false) + "; " + ex.getLocalizedMessage();
		return getErrorResponseEntity(HttpStatus.BAD_REQUEST, errorMessages, logMessage);
	}

	@ExceptionHandler({ CustomMethodArgumentNotValidException.class })
	public ResponseEntity<Object> handleCustomMethodArgumentNotValid(CustomMethodArgumentNotValidException ex,
			WebRequest request) {
		List<String> errorMessages = new ArrayList<>();
		errorMessages.add(ex.getMessage());
		String logMessage = ex.getStackTrace()[0].toString();
		return getErrorResponseEntity(HttpStatus.BAD_REQUEST, errorMessages, logMessage);
	}
	
	@ExceptionHandler({ MissingRequestHeaderException.class })
	public ResponseEntity<Object> handleMissingRequestHeader(MissingRequestHeaderException ex,
			WebRequest request) {
		List<String> errorMessages = new ArrayList<>();
		errorMessages.add(ex.getMessage());
		String logMessage = errorMessages.get(0).toString();
		return getErrorResponseEntity(HttpStatus.BAD_REQUEST, errorMessages, logMessage);
	}
	
	@Override
	public ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<MediaType> mediaTypes = ex.getSupportedMediaTypes();
		if (!CollectionUtils.isEmpty(mediaTypes)) {
			headers.setAccept(mediaTypes);
		}
		List<String> errorMessages = new ArrayList<>();
		errorMessages.add(ex.getMessage());
		String logMessage = request.getDescription(false) + "; " + ex.getLocalizedMessage();
		return getErrorResponseEntity(HttpStatus.BAD_REQUEST, errorMessages, logMessage);
	}

	@Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		List<String> errorMessages = new ArrayList<>();
		ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
			errorMessages.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
		});
		ex.getBindingResult().getGlobalErrors().forEach(fieldError -> {
			errorMessages.add(fieldError.getObjectName() + ": " + fieldError.getDefaultMessage());
		});
		String logMessage = request.getDescription(false) + "; " + ex.getLocalizedMessage();
		return getErrorResponseEntity(HttpStatus.BAD_REQUEST, errorMessages, logMessage);
	}

	@Override
	public ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Set<HttpMethod> supportedMethods = ex.getSupportedHttpMethods();
		if (!CollectionUtils.isEmpty(supportedMethods)) {
			headers.setAllow(supportedMethods);
		}
		List<String> errorMessages = new ArrayList<>();
		errorMessages.add(ex.getMessage());
		String logMessage = request.getDescription(false) + "; " + ex.getLocalizedMessage();
		return getErrorResponseEntity(HttpStatus.BAD_REQUEST, errorMessages, logMessage);
	}

	protected static ResponseEntity<Object> getErrorResponseEntity(HttpStatus httpStatus, List<String> errorMessages,
			String logMessage) {
		ErrorRestResponseDTO errDTO = new ErrorRestResponseDTO(httpStatus.value(), System.currentTimeMillis(),
				errorMessages);
		return new ResponseEntity<>(errDTO, httpStatus);
	}

}