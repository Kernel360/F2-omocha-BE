package org.omocha.domain.review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {

	Long addReview(ReviewCommand.AddReview addReview);

	Page<ReviewInfo.RetrieveReviews> retrieveReceivedReviews(
		ReviewCommand.RetrieveReviews retrieveReviews,
		Pageable pageable
	);

	Page<ReviewInfo.RetrieveReviews> retrieveGivenReviews(
		ReviewCommand.RetrieveReviews retrieveReviews,
		Pageable pageable
	);
}
