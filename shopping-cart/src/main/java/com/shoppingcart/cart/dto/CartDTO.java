package com.shoppingcart.cart.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.shoppingcart.common.util.DateUtil;

public class CartDTO {

	@JsonProperty("id")
	@NotNull(message = "id cannot be null.")
	private UUID id;

	@JsonProperty("email")
	@Email
	@NotBlank(message = "email cannot be null or empty.")
	@Size(max = 255, message = "email cannot be more than 255 characters.")
	private String email;

	@JsonProperty("created_date")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtil.DATE_FORMAT)
	@NotNull(message = "created date cannot be null.")
	private LocalDateTime createdDate;

	@JsonProperty("cart_amount")
	@NotNull(message = "cart amount cannot be null.")
	private BigDecimal cartAmount;

	@JsonProperty("order_products")
	private List<CartProductDTO> orderProducts = new ArrayList<>();

	public CartDTO() {
	}

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

	public BigDecimal getCartAmount() {
		return cartAmount;
	}

	public void setCartAmount(BigDecimal cartAmount) {
		this.cartAmount = cartAmount;
	}

	public List<CartProductDTO> getOrderProducts() {
		return orderProducts;
	}

	public void setOrderProducts(List<CartProductDTO> orderProducts) {
		this.orderProducts = orderProducts;
	}

}
