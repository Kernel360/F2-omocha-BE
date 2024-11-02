package org.omocha.infra.common.util;

import org.omocha.domain.common.util.PageSort;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PageSortImpl implements PageSort {
	@Override
	public Pageable sortPage(
		Pageable pageable,
		String sort,
		String direction
	) {
		Sort.Direction sortDirection = Sort.Direction.fromOptionalString(direction).orElse(Sort.Direction.DESC);
		return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(sortDirection, sort));
	}

}
