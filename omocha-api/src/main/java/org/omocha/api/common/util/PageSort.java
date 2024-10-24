package org.omocha.api.common.util;

import org.springframework.data.domain.Pageable;

public interface PageSort {

	Pageable sortPage(Pageable pageable, String sort, String direction);
}
