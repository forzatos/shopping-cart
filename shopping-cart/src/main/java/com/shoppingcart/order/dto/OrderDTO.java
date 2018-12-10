package com.shoppingcart.order.dto;

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

public class OrderDTO {

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

	@JsonProperty("order_amount")
	@NotNull(message = "order amount cannot be null.")
	private BigDecimal orderAmount;

	@JsonProperty("order_products")
	private List<OrderProductDTO> orderProducts = new ArrayList<>();

	@JsonProperty("cart_id")
	@NotNull(message = "cart id cannot be null.")
	private UUID cartId;

	public OrderDTO() {
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

	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	public List<OrderProductDTO> getOrderProducts() {
		return orderProducts;
	}

	public void setOrderProducts(List<OrderProductDTO> orderProducts) {
		this.orderProducts = orderProducts;
	}

	public UUID getCartId() {
		return cartId;
	}

	public void setCartId(UUID cartId) {
		this.cartId = cartId;
	}

}
