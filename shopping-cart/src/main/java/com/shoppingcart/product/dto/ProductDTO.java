package com.shoppingcart.product.dto;

import java.math.BigDecimal;
import java.util.UUID;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductDTO {

	@JsonProperty("id")
	@JsonInclude(Include.NON_NULL)
	private UUID id;

	@JsonProperty("name")
	@NotBlank(message = "name cannot be null or empty.")
	@Size(max = 255, message = "name cannot be more than 255 characters.")
	private String name;

	@JsonProperty("price")
	@NotNull(message = "price cannot be null.")
	@Min(value = 0, message = "price should be greater than 0.")
	@Max(value = 100000000, message = "price should not be greater than 100000000.")
	private BigDecimal price;

	public ProductDTO() {
	}

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

}
