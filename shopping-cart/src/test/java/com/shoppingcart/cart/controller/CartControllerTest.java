package com.shoppingcart.cart.controller;

import java.util.UUID;

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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.shoppingcart.cart.dto.CartDTO;
import com.shoppingcart.cart.service.CartService;
import com.shoppingcart.util.TestHelperUtilities;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CartControllerTest {

	private String uuid;

	@MockBean
	private CartService cartService;

	@Autowired
	private TestRestTemplate template;

	@Before
	public void setUp() throws Exception {
		uuid = UUID.randomUUID().toString();
	}

	@Test
	public void testCreateCartForEmailWithValidHeaderValues() throws Exception {
		Mockito.when(cartService.getCreatedCartForEmail(Mockito.any(String.class))).thenReturn(new CartDTO());
		HttpEntity<Object> response = TestHelperUtilities.getHttpEntity("",
				TestHelperUtilities.getHttpHeadersWithUserEmailSet());
		ResponseEntity<CartDTO> result = template.exchange("/carts", HttpMethod.GET, response, CartDTO.class);
		Assert.assertEquals(HttpStatus.OK.value(), result.getStatusCode().value());
	}

	@Test
	public void tetCreateCartForEmailWithInvalidHeaderValues() throws Exception {
		Mockito.when(cartService.getCreatedCartForEmail(Mockito.any(String.class))).thenReturn(new CartDTO());
		HttpEntity<Object> response = TestHelperUtilities.getHttpEntity("",
				TestHelperUtilities.getHttpHeadersMinimal());
		ResponseEntity<CartDTO> result = template.exchange("/carts", HttpMethod.GET, response, CartDTO.class);
		Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), result.getStatusCode().value());
	}

	@Test
	public void testFindCartByEmailAndIdThatExists() throws Exception {
		Mockito.when(cartService.findCartByIdAndEmail(Mockito.any(UUID.class), Mockito.any(String.class)))
				.thenReturn(new CartDTO());
		HttpEntity<Object> response = TestHelperUtilities.getHttpEntity("",
				TestHelperUtilities.getHttpHeadersWithUserEmailSet());
		ResponseEntity<CartDTO> result = template.exchange("/carts/" + uuid, HttpMethod.GET, response, CartDTO.class);
		Assert.assertEquals(HttpStatus.OK.value(), result.getStatusCode().value());
	}

	@Test
	public void testFindCartByEmailAndIdThatDoesNotExists() throws Exception {
		Mockito.when(cartService.findCartByIdAndEmail(Mockito.any(UUID.class), Mockito.any(String.class)))
				.thenReturn(null);
		HttpEntity<Object> response = TestHelperUtilities.getHttpEntity("",
				TestHelperUtilities.getHttpHeadersWithUserEmailSet());
		ResponseEntity<CartDTO> result = template.exchange("/carts/" + uuid, HttpMethod.GET, response, CartDTO.class);
		Assert.assertEquals(HttpStatus.NOT_FOUND.value(), result.getStatusCode().value());
	}

}
