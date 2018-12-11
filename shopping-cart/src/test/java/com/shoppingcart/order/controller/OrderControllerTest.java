package com.shoppingcart.order.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.shoppingcart.order.dto.OrderDTO;
import com.shoppingcart.order.service.OrderService;
import com.shoppingcart.util.TestHelperUtilities;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class OrderControllerTest {

	private HttpHeaders headersWithoutKeyValues;

	@MockBean
	private OrderService orderService;

	@Autowired
	private TestRestTemplate template;

	@Before
	public void setUp() throws Exception {
		headersWithoutKeyValues = new HttpHeaders();
	}

	@Test
	public void testStatusForFinOrdersByDateSearch() throws Exception {
		Mockito.when(orderService.findByDateSearch(Mockito.any(LocalDateTime.class), Mockito.any(LocalDateTime.class)))
				.thenReturn(new ArrayList<OrderDTO>());
		HttpEntity<Object> response = TestHelperUtilities.getHttpEntity("", headersWithoutKeyValues);
		ResponseEntity<List<OrderDTO>> result = template.exchange("/orders", HttpMethod.GET, response,
				new ParameterizedTypeReference<List<OrderDTO>>() {
				});
		Assert.assertEquals(HttpStatus.OK.value(), result.getStatusCode().value());
	}

}
