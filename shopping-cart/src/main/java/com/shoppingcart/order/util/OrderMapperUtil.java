package com.shoppingcart.order.util;

import java.util.ArrayList;
import java.util.List;

import com.shoppingcart.order.domain.Order;
import com.shoppingcart.order.domain.OrderProduct;
import com.shoppingcart.order.dto.OrderDTO;
import com.shoppingcart.order.dto.OrderProductDTO;

public class OrderMapperUtil {

	public static OrderDTO getOrderDTO(Order order) {
		if (order == null) {
			return null;
		}
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setId(order.getId());
		orderDTO.setEmail(order.getEmail());
		orderDTO.setCreatedDate(order.getCreatedDate());
		orderDTO.setOrderAmount(order.getOrderAmount());
		orderDTO.setOrderProducts(getProductOrderDTOList(order.getOrderProducts()));
		orderDTO.setCartId(order.getCartId());
		return orderDTO;
	}

	public static List<OrderDTO> getOrderDTOList(List<Order> orders) {
		List<OrderDTO> orderDTOs = new ArrayList<>();
		if (orders == null) {
			return orderDTOs;
		}
		orders.forEach(order -> {
			orderDTOs.add(getOrderDTO(order));
		});
		return orderDTOs;
	}

	public static Order getOrderEntity(OrderDTO orderDTO) {
		if (orderDTO == null) {
			return null;
		}
		Order order = new Order();
		order.setId(orderDTO.getId());
		order.setCreatedDate(orderDTO.getCreatedDate());
		order.setEmail(orderDTO.getEmail());
		order.setCartId(orderDTO.getCartId());
		order.setOrderAmount(orderDTO.getOrderAmount());
		order.setOrderProducts(getOrderProductEntityList(order, orderDTO.getOrderProducts()));
		return order;
	}

	private static OrderProduct getOrderProductEntity(Order order, OrderProductDTO orderProductDTO) {
		if (orderProductDTO == null) {
			return null;
		}
		OrderProduct orderProduct = new OrderProduct();
		orderProduct.setId(orderProductDTO.getId());
		orderProduct.setName(orderProductDTO.getName());
		orderProduct.setOrder(order);
		orderProduct.setPrice(orderProductDTO.getPrice());
		orderProduct.setProductId(orderProductDTO.getProductId());
		return orderProduct;
	}

	private static List<OrderProduct> getOrderProductEntityList(Order order, List<OrderProductDTO> orderDTOs) {
		List<OrderProduct> orderProducts = new ArrayList<>();
		if (orderDTOs == null) {
			return orderProducts;
		}
		orderDTOs.forEach(orderProductDTO -> {
			orderProducts.add(getOrderProductEntity(order, orderProductDTO));
		});
		return orderProducts;
	}

	private static OrderProductDTO getOrderProductDTO(OrderProduct orderProduct) {
		if (orderProduct == null) {
			return null;
		}
		OrderProductDTO orderProductDTO = new OrderProductDTO();
		orderProductDTO.setId(orderProduct.getId());
		orderProductDTO.setName(orderProduct.getName());
		orderProductDTO.setOrderId(orderProduct.getOrder().getId());
		orderProductDTO.setPrice(orderProduct.getPrice());
		orderProductDTO.setProductId(orderProduct.getProductId());
		return orderProductDTO;
	}

	private static List<OrderProductDTO> getProductOrderDTOList(List<OrderProduct> orderProducts) {
		List<OrderProductDTO> orderProductDTOs = new ArrayList<>();
		if (orderProducts == null) {
			return orderProductDTOs;
		}
		orderProducts.forEach(orderProduct -> {
			orderProductDTOs.add(getOrderProductDTO(orderProduct));
		});
		return orderProductDTOs;
	}

}
