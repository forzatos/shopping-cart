package com.shoppingcart.order.dto;

import java.math.BigDecimal;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderProductDTO {

	@JsonProperty("id")
	@NotNull(message = "id cannot be null.")
	private UUID id;

	@JsonProperty("name")
	@NotBlank(message = "name cannot be null or empty.")
	@Size(max = 255, message = "name cannot be more than 255 characters.")
	private String name;

	@JsonProperty("price")
	@NotNull(message = "price cannot be null.")
	private BigDecimal price;

	@JsonProperty("order_id")
	@NotNull(message = "order id cannot be null.")
	private UUID orderId;

	@JsonProperty("product_id")
	@NotNull(message = "product id cannot be null.")
	private UUID productId;

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

	public UUID getOrderId() {
		return orderId;
	}

	public void setOrderId(UUID orderId) {
		this.orderId = orderId;
	}

	public UUID getProductId() {
		return productId;
	}

	public void setProductId(UUID productId) {
		this.productId = productId;
	}

}
