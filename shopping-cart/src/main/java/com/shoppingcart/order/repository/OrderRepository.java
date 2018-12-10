package com.shoppingcart.order.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.shoppingcart.order.domain.Order;

@RepositoryRestResource(exported = false)
public interface OrderRepository extends CrudRepository<Order, Long> {

	public List<Order> findAll();
	
	public List<Order> findByCreatedDateGreaterThanEqualAndCreatedDateLessThanEqual(LocalDateTime startDate, LocalDateTime endDate);

	public List<Order> findByCreatedDateGreaterThanEqual(LocalDateTime startDate);

	public List<Order> findByCreatedDateLessThanEqual(LocalDateTime endDate);
	
}
