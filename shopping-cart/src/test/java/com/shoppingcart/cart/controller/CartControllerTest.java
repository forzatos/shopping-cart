package com.shoppingcart.cart.controller;

import java.util.ArrayList;
import java.util.List;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.shoppingcart.cart.dto.CartDTO;
import com.shoppingcart.cart.dto.CartProductDTO;
import com.shoppingcart.cart.service.CartService;
import com.shoppingcart.util.TestHelperUtilities;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CartControllerTest {

	private String uuid;
	private HttpHeaders headersWithoutKeyValues;
	private HttpHeaders headersWithCorrectEmail;
	private HttpHeaders headersWithInvalidEmail;

	@MockBean
	private CartService cartService;

	@Autowired
	private TestRestTemplate template;

	@Before
	public void setUp() throws Exception {
		uuid = UUID.randomUUID().toString();
		headersWithoutKeyValues = new HttpHeaders();
		headersWithCorrectEmail = new HttpHeaders();
		headersWithCorrectEmail.set("user-email", "test@testtest.com");
		headersWithInvalidEmail = new HttpHeaders();
		headersWithInvalidEmail.set("user-email", "");
	}

	@Test
	public void testCreateCartForEmailWithValidHeaderValues() throws Exception {
		Mockito.when(cartService.getCreatedCartForEmail(Mockito.any(String.class))).thenReturn(new CartDTO());
		HttpEntity<Object> response = TestHelperUtilities.getHttpEntity("", headersWithCorrectEmail);
		ResponseEntity<CartDTO> result = template.exchange("/carts", HttpMethod.GET, response, CartDTO.class);
		Assert.assertEquals(HttpStatus.OK.value(), result.getStatusCode().value());
	}

	@Test
	public void tetsCreateCartForEmailWithNoHeaderValues() throws Exception {
		Mockito.when(cartService.getCreatedCartForEmail(Mockito.any(String.class))).thenReturn(new CartDTO());
		HttpEntity<Object> response = TestHelperUtilities.getHttpEntity("", headersWithoutKeyValues);
		ResponseEntity<CartDTO> result = template.exchange("/carts", HttpMethod.GET, response, CartDTO.class);
		Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), result.getStatusCode().value());
	}

	@Test
	public void tetsCreateCartForEmailWithInvalidEmailHeaderValues() throws Exception {
		Mockito.when(cartService.getCreatedCartForEmail(Mockito.any(String.class))).thenReturn(new CartDTO());
		HttpEntity<Object> response = TestHelperUtilities.getHttpEntity("", headersWithInvalidEmail);
		ResponseEntity<CartDTO> result = template.exchange("/carts", HttpMethod.GET, response, CartDTO.class);
		Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), result.getStatusCode().value());
	}

	@Test
	public void testFindCartByEmailAndIdWithInvalidEmailHeaderValues() throws Exception {
		Mockito.when(cartService.findCartByIdAndEmail(Mockito.any(UUID.class), Mockito.any(String.class)))
				.thenReturn(new CartDTO());
		HttpEntity<Object> response = TestHelperUtilities.getHttpEntity("", headersWithInvalidEmail);
		ResponseEntity<CartDTO> result = template.exchange("/carts/" + uuid, HttpMethod.GET, response, CartDTO.class);
		Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), result.getStatusCode().value());
	}

	@Test
	public void testFindCartByEmailAndIdThatExists() throws Exception {
		Mockito.when(cartService.findCartByIdAndEmail(Mockito.any(UUID.class), Mockito.any(String.class)))
				.thenReturn(new CartDTO());
		HttpEntity<Object> response = TestHelperUtilities.getHttpEntity("", headersWithCorrectEmail);
		ResponseEntity<CartDTO> result = template.exchange("/carts/" + uuid, HttpMethod.GET, response, CartDTO.class);
		Assert.assertEquals(HttpStatus.OK.value(), result.getStatusCode().value());
	}

	@Test
	public void testFindCartByEmailAndIdThatDoesNotExists() throws Exception {
		Mockito.when(cartService.findCartByIdAndEmail(Mockito.any(UUID.class), Mockito.any(String.class)))
				.thenReturn(null);
		HttpEntity<Object> response = TestHelperUtilities.getHttpEntity("", headersWithCorrectEmail);
		ResponseEntity<CartDTO> result = template.exchange("/carts/" + uuid, HttpMethod.GET, response, CartDTO.class);
		Assert.assertEquals(HttpStatus.NOT_FOUND.value(), result.getStatusCode().value());
	}

	@Test
	public void testAddProductToCart() throws Exception {
		Mockito.when(cartService.findCartByIdAndEmail(Mockito.any(UUID.class), Mockito.any(String.class)))
				.thenReturn(new CartDTO());
		HttpEntity<Object> response = TestHelperUtilities.getHttpEntity("", headersWithCorrectEmail);
		ResponseEntity<CartDTO> result = template.exchange("/carts/" + uuid + "/add/" + uuid, HttpMethod.GET, response,
				CartDTO.class);
		Assert.assertEquals(HttpStatus.OK.value(), result.getStatusCode().value());
	}

	@Test
	public void testAddProductToCartNotFound() throws Exception {
		Mockito.when(cartService.findCartByIdAndEmail(Mockito.any(UUID.class), Mockito.any(String.class)))
				.thenReturn(null);
		HttpEntity<Object> response = TestHelperUtilities.getHttpEntity("", headersWithCorrectEmail);
		ResponseEntity<CartDTO> result = template.exchange("/carts/" + uuid + "/add/" + uuid, HttpMethod.GET, response,
				CartDTO.class);
		Assert.assertEquals(HttpStatus.NOT_FOUND.value(), result.getStatusCode().value());
	}

	@Test
	public void testRemoveProductFromCartNotFound() throws Exception {
		Mockito.when(cartService.findCartByIdAndEmail(Mockito.any(UUID.class), Mockito.any(String.class)))
				.thenReturn(null);
		HttpEntity<Object> response = TestHelperUtilities.getHttpEntity("", headersWithCorrectEmail);
		ResponseEntity<CartDTO> result = template.exchange("/carts/" + uuid + "/remove/" + uuid, HttpMethod.GET,
				response, CartDTO.class);
		Assert.assertEquals(HttpStatus.NOT_FOUND.value(), result.getStatusCode().value());
	}

	@Test
	public void testRemoveProductFromCart() throws Exception {
		Mockito.when(cartService.findCartByIdAndEmail(Mockito.any(UUID.class), Mockito.any(String.class)))
				.thenReturn(new CartDTO());
		HttpEntity<Object> response = TestHelperUtilities.getHttpEntity("", headersWithCorrectEmail);
		ResponseEntity<CartDTO> result = template.exchange("/carts/" + uuid + "/remove/" + uuid, HttpMethod.GET,
				response, CartDTO.class);
		Assert.assertEquals(HttpStatus.OK.value(), result.getStatusCode().value());
	}

	@Test
	public void testTriggerCheckoutCartNotFound() throws Exception {
		Mockito.when(cartService.findCartByIdAndEmail(Mockito.any(UUID.class), Mockito.any(String.class)))
				.thenReturn(null);
		HttpEntity<Object> response = TestHelperUtilities.getHttpEntity("", headersWithCorrectEmail);
		ResponseEntity<Void> result = template.exchange("/carts/checkout/" + uuid, HttpMethod.GET, response,
				Void.class);
		Assert.assertEquals(HttpStatus.NOT_FOUND.value(), result.getStatusCode().value());
	}

	@Test
	public void testTriggerCheckoutCartFoundWithoutProducts() throws Exception {
		Mockito.when(cartService.findCartByIdAndEmail(Mockito.any(UUID.class), Mockito.any(String.class)))
				.thenReturn(new CartDTO());
		HttpEntity<Object> response = TestHelperUtilities.getHttpEntity("", headersWithCorrectEmail);
		ResponseEntity<Void> result = template.exchange("/carts/checkout/" + uuid, HttpMethod.GET, response,
				Void.class);
		Assert.assertEquals(HttpStatus.NOT_FOUND.value(), result.getStatusCode().value());
	}

	@Test
	public void testTriggerCheckout() throws Exception {
		List<CartProductDTO> cpDTOs = new ArrayList<>();
		cpDTOs.add(new CartProductDTO());
		CartDTO cartDTO = Mockito.mock(CartDTO.class);
		cartDTO.setOrderProducts(cpDTOs);
		Mockito.when(cartDTO.getOrderProducts()).thenReturn(cpDTOs);
		Mockito.when(cartService.findCartByIdAndEmail(Mockito.any(UUID.class), Mockito.any(String.class)))
				.thenReturn(cartDTO);
		HttpEntity<Object> response = TestHelperUtilities.getHttpEntity("", headersWithCorrectEmail);
		ResponseEntity<Void> result = template.exchange("/carts/checkout/" + uuid, HttpMethod.GET, response,
				Void.class);
		Assert.assertEquals(HttpStatus.OK.value(), result.getStatusCode().value());
	}
}
