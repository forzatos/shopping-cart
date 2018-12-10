package com.shoppingcart.cart.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.shoppingcart.cart.domain.Cart;

@RepositoryRestResource(exported = false)
public interface CartRepository extends CrudRepository<Cart, UUID> {

	public List<Cart> findAll();

	public Cart findByIdAndEmailIgnoreCase(UUID id, String email);

}
