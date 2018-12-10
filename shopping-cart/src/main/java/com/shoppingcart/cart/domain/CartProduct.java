package com.shoppingcart.cart.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name = "CartProduct")
@Table(name = "cart_product")
public class CartProduct {

	public CartProduct() {
	}

	@Id
	@Column(name = "id", columnDefinition = "BINARY(16)")
	@NotNull(message = "id cannot be null.")
	private UUID id;

	@Column(name = "name")
	@NotBlank(message = "name cannot be null or empty.")
	@Size(max = 255, message = "name cannot be more than 255 characters.")
	private String name;

	@Column(name = "price")
	@NotNull(message = "price cannot be null.")
	private BigDecimal price;

	@Column(name = "cart_product_id")
	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "cartProducts")
	private List<Cart> carts = new ArrayList<>();

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public List<Cart> getCarts() {
		return carts;
	}

	public void setCarts(List<Cart> carts) {
		this.carts = carts;
	}

}
