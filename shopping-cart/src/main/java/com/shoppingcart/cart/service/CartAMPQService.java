package com.shoppingcart.cart.service;

import com.shoppingcart.cart.dto.CartDTO;

public interface CartAMPQService {

	public void sendBrokerEvent(String brokerEvent, CartDTO cartDTO);

}
