package org.auction.client.common.dto;

import java.util.List;

import org.springframework.data.domain.Slice;

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