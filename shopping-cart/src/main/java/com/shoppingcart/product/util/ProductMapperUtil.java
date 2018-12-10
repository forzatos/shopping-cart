package com.shoppingcart.product.util;

import java.util.ArrayList;
import java.util.List;

import com.shoppingcart.product.domain.Product;
import com.shoppingcart.product.dto.ProductDTO;

public class ProductMapperUtil {

	public static ProductDTO getProductDTO(Product product) {
		if (product == null) {
			return null;
		}
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(product.getId());
		productDTO.setName(product.getName());
		productDTO.setPrice(product.getPrice());
		return productDTO;
	}

	public static List<ProductDTO> getProductDTOList(List<Product> products) {
		List<ProductDTO> productDTOs = new ArrayList<>();
		if (products == null) {
			return null;
		}
		products.forEach(product -> {
			productDTOs.add(getProductDTO(product));
		});
		return productDTOs;
	}

	public static Product getProductEntity(ProductDTO productDTO) {
		if (productDTO == null) {
			return null;
		}
		Product product = new Product();
		product.setId(productDTO.getId());
		product.setName(productDTO.getName());
		product.setPrice(productDTO.getPrice());
		return product;
	}

	public static List<Product> getProductEntityList(List<ProductDTO> productDTOs) {
		List<Product> products = new ArrayList<>();
		if (productDTOs == null) {
			return null;
		}
		productDTOs.forEach(productDTO -> {
			products.add(getProductEntity(productDTO));
		});
		return products;
	}

}
