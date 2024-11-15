package org.omocha.api.application;

import org.omocha.domain.auction.review.ReviewCommand;
import org.omocha.domain.auction.review.ReviewInfo;
import org.omocha.domain.auction.review.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewFacade {

	private final ReviewService reviewService;

	public Long addReview(ReviewCommand.AddReview addReview) {
		return reviewService.addReview(addReview);
	}

	public Page<ReviewInfo.RetrieveReviews> retrieveReceivedReviews(
		ReviewCommand.RetrieveReviews retrieveReviews,
		Pageable pageable
	) {
		return reviewService.retrieveReceivedReviews(retrieveReviews, pageable);
	}

	public Page<ReviewInfo.RetrieveReviews> retrieveGivenReviews(
		ReviewCommand.RetrieveReviews retrieveReviews,
		Pageable pageable
	) {
		return reviewService.retrieveGivenReviews(retrieveReviews, pageable);
	}
}
