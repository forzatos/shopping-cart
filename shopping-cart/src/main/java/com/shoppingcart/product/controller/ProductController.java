package com.shoppingcart.product.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcart.common.exception.CustomMethodArgumentNotValidException;
import com.shoppingcart.product.dto.ProductDTO;
import com.shoppingcart.product.exception.ProductNotFoundException;
import com.shoppingcart.product.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

	private final ProductService productService;

	@Autowired
	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@PostMapping
	public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
		ProductDTO newProductDTO = productService.save(productDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(newProductDTO);
	}

	@GetMapping
	public ResponseEntity<List<ProductDTO>> findAllProducts() {
		return ResponseEntity.ok(productService.findAll());
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<ProductDTO> findProductById(@PathVariable(name = "id") UUID id) {
		ProductDTO productDTO = productService.findById(id);
		if (productDTO == null) {
			throw new ProductNotFoundException("product with id " + id + " not found.");
		}
		return ResponseEntity.ok(productDTO);
	}

	@PutMapping
	public ResponseEntity<?> updateProduct(@Valid @RequestBody ProductDTO productDTO) {
		if (productDTO.getId() == null) {
			throw new CustomMethodArgumentNotValidException("product id cannot be null.");
		}
		ProductDTO existingProductDTO = productService.findById(productDTO.getId());
		if (existingProductDTO == null) {
			throw new ProductNotFoundException(
					"product with id " + productDTO.getId() + " not found, could not update.");
		}
		productService.update(productDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> deleteProductById(@PathVariable(name = "id") UUID id) {
		ProductDTO productDTO = productService.findById(id);
		if (productDTO == null) {
			throw new ProductNotFoundException("product with id " + id + " not found, could not delete.");
		}
		productService.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
