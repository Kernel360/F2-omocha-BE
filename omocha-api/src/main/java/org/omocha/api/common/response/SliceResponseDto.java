package org.omocha.api.common.response;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;

public class SliceResponseDto {
	public record SliceResponse<T>(
		List<T> content,
		SortResponse sort,
		int currentPage,
		int size,
		boolean first,
		boolean last,
		boolean hasNext
	) {
		public SliceResponse(Slice<T> slice) {
			this(
				slice.getContent(),
				new SortResponse(slice.getSort()),
				slice.getNumber() + 1,
				slice.getSize(),
				slice.isFirst(),
				slice.isLast(),
				slice.hasNext()
			);
		}
	}

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
}
