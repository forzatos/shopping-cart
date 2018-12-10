package com.shoppingcart.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class TestHelperUtilities {

	private static final ObjectMapper MAPPER = new ObjectMapper();
	static {
		MAPPER.registerModule(new JavaTimeModule());
		MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
	}

	public static HttpEntity<Object> getHttpEntity(Object value, HttpHeaders headers) throws Exception {
		String body = MAPPER.writeValueAsString(value);
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new HttpEntity<Object>(body, headers);
	}

}
