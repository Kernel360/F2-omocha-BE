package org.omocha.domain.auction.review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewReader {

	boolean checkExistReview(Long auctionId, Long memberId);

	Page<ReviewInfo.RetrieveReviews> getReviews(
		ReviewCommand.RetrieveReviews retrieveReviews,
		Pageable pageable
	);

	Rating getAverageRating(Long recipientId);
}
