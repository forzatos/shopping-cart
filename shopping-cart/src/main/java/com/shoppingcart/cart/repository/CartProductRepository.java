package com.shoppingcart.cart.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.shoppingcart.cart.domain.CartProduct;

@RepositoryRestResource(exported = false)
public interface CartProductRepository extends CrudRepository<CartProduct, UUID> {

}
