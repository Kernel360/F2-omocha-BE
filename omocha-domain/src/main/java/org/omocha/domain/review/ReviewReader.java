package org.omocha.domain.review;

import org.omocha.domain.review.rating.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewReader {

	boolean checkExistReview(Long auctionId, Long memberId);

	Page<ReviewInfo.RetrieveReviews> getReceivedReviews(
		ReviewCommand.RetrieveReviews retrieveReviews,
		Pageable pageable
	);

	Page<ReviewInfo.RetrieveReviews> getGivenReviews(
		ReviewCommand.RetrieveReviews retrieveReviews,
		Pageable pageable
	);

	Rating getAverageRating(Long recipientId);
}
