package com.shoppingcart.common.dto;

import java.util.List;

public class ErrorRestResponseDTO {

	private int errorCode;
	private long traceCode;
	private List<String> errorMessages;

	public ErrorRestResponseDTO(int errorCode, long traceCode, List<String> errorMessages) {
		this.errorCode = errorCode;
		this.traceCode = traceCode;
		this.errorMessages = errorMessages;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public long getTraceCode() {
		return traceCode;
	}

	public List<String> getErrorMessages() {
		return errorMessages;
	}

}
