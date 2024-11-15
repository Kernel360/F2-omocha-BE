package org.omocha.infra.review.repository;

import org.omocha.domain.review.ReviewCommand;
import org.omocha.domain.review.ReviewInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewRepositoryCustom {

	Page<ReviewInfo.RetrieveReviews> getReceivedReviews(
		ReviewCommand.RetrieveReviews retrieveReviews,
		Pageable pageable
	);

	Page<ReviewInfo.RetrieveReviews> getGivenReviews(
		ReviewCommand.RetrieveReviews retrieveReviews,
		Pageable pageable
	);
}
