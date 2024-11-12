package org.omocha.domain.auction.review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {

	Long addReview(ReviewCommand.AddReview addReview);

	Page<ReviewInfo.RetrieveReviews> retrieveReviews(
		ReviewCommand.RetrieveReviews retrieveReviews,
		Pageable pageable
	);
}
