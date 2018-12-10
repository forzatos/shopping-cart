package com.shoppingcart.product.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingcart.common.util.ConstantValuesUtil;
import com.shoppingcart.product.domain.Product;
import com.shoppingcart.product.dto.ProductDTO;
import com.shoppingcart.product.repository.ProductRepository;
import com.shoppingcart.product.util.ProductMapperUtil;

@Service
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;
	private final ProductAMPQService productAMPQService;

	@Autowired
	public ProductServiceImpl(ProductRepository productRepository, ProductAMPQService productAMPQService) {
		this.productRepository = productRepository;
		this.productAMPQService = productAMPQService;
	}

	@Override
	public List<ProductDTO> findAll() {
		List<Product> products = productRepository.findAll();
		return ProductMapperUtil.getProductDTOList(products);
	}

	@Override
	public ProductDTO save(ProductDTO productDTO) {
		productDTO.setId(null);
		return saveOrUpdate(ConstantValuesUtil.EVENT_CREATED, productDTO);
	}

	@Override
	public ProductDTO findById(UUID id) {
		Product product = productRepository.findById(id).orElse(null);
		return ProductMapperUtil.getProductDTO(product);
	}

	@Override
	public void deleteById(UUID id) {
		productRepository.deleteById(id);
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(id);
		productAMPQService.sendBrokerEvent(ConstantValuesUtil.EVENT_DELETED, productDTO);
	}

	@Override
	public ProductDTO update(ProductDTO productDTO) {
		return saveOrUpdate(ConstantValuesUtil.EVENT_UPDATED, productDTO);
	}

	private ProductDTO saveOrUpdate(String brokerEvent, ProductDTO productDTO) {
		Product product = ProductMapperUtil.getProductEntity(productDTO);
		ProductDTO newProductDTO = ProductMapperUtil.getProductDTO(productRepository.save(product));
		productAMPQService.sendBrokerEvent(brokerEvent, newProductDTO);
		return newProductDTO;
	}

}
