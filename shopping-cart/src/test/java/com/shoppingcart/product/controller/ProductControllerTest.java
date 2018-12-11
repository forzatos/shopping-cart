package com.shoppingcart.product.controller;

import java.math.BigDecimal;
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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.shoppingcart.product.dto.ProductDTO;
import com.shoppingcart.product.service.ProductService;
import com.shoppingcart.util.TestHelperUtilities;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {

	private String uuid;
	private HttpHeaders headersWithoutKeyValues;
	private ProductDTO productDTO;

	@MockBean
	private ProductService productService;

	@Autowired
	private TestRestTemplate template;

	@Before
	public void setUp() throws Exception {
		uuid = UUID.randomUUID().toString();
		headersWithoutKeyValues = new HttpHeaders();
		productDTO = new ProductDTO();
		productDTO.setId(UUID.randomUUID());
		productDTO.setName("Test name");
		productDTO.setPrice(new BigDecimal("10.0"));
	}

	@Test
	public void testStatusForCreateNewOrder() throws Exception {
		Mockito.when(productService.save(Mockito.any(ProductDTO.class))).thenReturn(productDTO);
		HttpEntity<Object> response = TestHelperUtilities.getHttpEntity(productDTO, headersWithoutKeyValues);
		ResponseEntity<ProductDTO> result = template.exchange("/products", HttpMethod.POST, response, ProductDTO.class);
		Assert.assertEquals(HttpStatus.CREATED.value(), result.getStatusCode().value());
	}

	@Test
	public void testStatusFindAllProducts() throws Exception {
		Mockito.when(productService.findAll()).thenReturn(new ArrayList<ProductDTO>());
		HttpEntity<Object> response = TestHelperUtilities.getHttpEntity("", headersWithoutKeyValues);
		ResponseEntity<List<ProductDTO>> result = template.exchange("/products", HttpMethod.GET, response,
				new ParameterizedTypeReference<List<ProductDTO>>() {
				});
		Assert.assertEquals(HttpStatus.OK.value(), result.getStatusCode().value());
	}

	@Test
	public void testStatuFindProductByIdThatExists() throws Exception {
		Mockito.when(productService.findById(Mockito.any(UUID.class))).thenReturn(productDTO);
		ResponseEntity<ProductDTO> result = template.exchange("/products/" + uuid, HttpMethod.GET, null,
				ProductDTO.class);
		Assert.assertEquals(HttpStatus.OK.value(), result.getStatusCode().value());
	}

	@Test
	public void testStatusFindProductByIdThatDoesNotExists() throws Exception {
		Mockito.when(productService.findById(Mockito.any(UUID.class))).thenReturn(null);
		ResponseEntity<ProductDTO> result = template.exchange("/products/" + uuid, HttpMethod.GET, null,
				ProductDTO.class);
		Assert.assertEquals(HttpStatus.NOT_FOUND.value(), result.getStatusCode().value());
	}

	@Test
	public void testStatusDeleteProductByIdThatExists() throws Exception {
		Mockito.when(productService.findById(Mockito.any(UUID.class))).thenReturn(productDTO);
		ResponseEntity<ProductDTO> result = template.exchange("/products/" + uuid, HttpMethod.DELETE, null,
				ProductDTO.class);
		Assert.assertEquals(HttpStatus.OK.value(), result.getStatusCode().value());
	}

	@Test
	public void testStatusDeleteProductByIdThatDoesNotExists() throws Exception {
		Mockito.when(productService.findById(Mockito.any(UUID.class))).thenReturn(null);
		ResponseEntity<ProductDTO> result = template.exchange("/products/" + uuid, HttpMethod.DELETE, null,
				ProductDTO.class);
		Assert.assertEquals(HttpStatus.NOT_FOUND.value(), result.getStatusCode().value());
	}

	@Test
	public void testStatusUpdateProductByIdThaExists() throws Exception {
		Mockito.when(productService.findById(Mockito.any(UUID.class))).thenReturn(productDTO);
		HttpEntity<Object> response = TestHelperUtilities.getHttpEntity(productDTO, headersWithoutKeyValues);
		ResponseEntity<ProductDTO> result = template.exchange("/products", HttpMethod.PUT, response, ProductDTO.class);
		Assert.assertEquals(HttpStatus.OK.value(), result.getStatusCode().value());
	}

	@Test
	public void testStatusUpdateProductByIdNullValue() throws Exception {
		productDTO.setId(null);
		HttpEntity<Object> response = TestHelperUtilities.getHttpEntity(productDTO, headersWithoutKeyValues);
		ResponseEntity<ProductDTO> result = template.exchange("/products", HttpMethod.PUT, response, ProductDTO.class);
		Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), result.getStatusCode().value());
	}

	@Test
	public void testStatusUpdateProductByIdThatDoesNotExists() throws Exception {
		Mockito.when(productService.findById(Mockito.any(UUID.class))).thenReturn(null);
		HttpEntity<Object> response = TestHelperUtilities.getHttpEntity(productDTO, headersWithoutKeyValues);
		ResponseEntity<ProductDTO> result = template.exchange("/products", HttpMethod.PUT, response, ProductDTO.class);
		Assert.assertEquals(HttpStatus.NOT_FOUND.value(), result.getStatusCode().value());
	}

}
