package com.shoppingcart.cart.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

@Entity(name = "Cart")
@Table(name = "cart")
public class Cart {

	public Cart() {
	}

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "id", columnDefinition = "BINARY(16)")
	private UUID id;

	@Email
	@NotBlank(message = "email cannot be null or empty.")
	@Size(max = 255, message = "email cannot be more than 255 characters.")
	@Column(name = "email")
	private String email;

	@Column(name = "created_date")
	@NotNull(message = "created date cannot be null.")
	private LocalDateTime createdDate;

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST })
	@JoinTable(name = "cart_cart_product", joinColumns = @JoinColumn(name = "cart_id"), inverseJoinColumns = @JoinColumn(name = "cart_product_id"))
	private List<CartProduct> cartProducts = new ArrayList<>();

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public List<CartProduct> getCartProducts() {
		return cartProducts;
	}

	public void setCartProducts(List<CartProduct> cartProducts) {
		this.cartProducts = cartProducts;
	}

	@Transient
	public BigDecimal getCartAmount() {
		BigDecimal cartAmount = BigDecimal.ZERO;
		if (getCartProducts() == null || getCartProducts().isEmpty()) {
			return cartAmount;
		}
		for (CartProduct cartProduct:getCartProducts()) {
			cartAmount = cartAmount.add(cartProduct.getPrice());
		}
		return cartAmount;
	}

}
