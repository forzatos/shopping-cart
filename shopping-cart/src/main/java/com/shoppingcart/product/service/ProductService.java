package com.shoppingcart.product.service;

import java.util.List;
import java.util.UUID;

import com.shoppingcart.product.dto.ProductDTO;

public interface ProductService {

	public List<ProductDTO> findAll();

	public ProductDTO save(ProductDTO productDTO);

	public ProductDTO findById(UUID id);

	public void deleteById(UUID id);

	public ProductDTO update(ProductDTO productDTO);

}
