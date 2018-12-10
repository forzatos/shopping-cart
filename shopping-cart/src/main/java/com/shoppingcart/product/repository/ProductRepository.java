package com.shoppingcart.product.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.shoppingcart.product.domain.Product;

@RepositoryRestResource(exported = false)
public interface ProductRepository extends CrudRepository<Product, UUID> {

	public List<Product> findAll();
	
}
