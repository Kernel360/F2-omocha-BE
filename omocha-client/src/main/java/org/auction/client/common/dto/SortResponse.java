package org.auction.client.common.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;

public record SortResponse(
	List<OrderResponse> orders
) {
	public SortResponse(Sort sort) {
		this(
			sort.stream()
				.map(OrderResponse::new)
				.collect(Collectors.toList())
		);
	}

	public record OrderResponse(String property, Sort.Direction direction) {
		public OrderResponse(Sort.Order order) {
			this(order.getProperty(), order.getDirection());
		}
	}
}