package com.shoppingcart.product.service;

import com.shoppingcart.product.dto.ProductDTO;

public interface ProductAMPQService {

	public void sendBrokerEvent(String brokerEvent, ProductDTO productDTO);

}
