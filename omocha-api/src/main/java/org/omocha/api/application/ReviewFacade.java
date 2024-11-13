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

	// 리뷰 조회
	public Page<ReviewInfo.RetrieveReviews> retrieveReviews(
		ReviewCommand.RetrieveReviews retrieveReviews,
		Pageable pageable
	) {
		return reviewService.retrieveReviews(retrieveReviews, pageable);
	}

	// 평점 평균 조회는 member쪽에서 만들어야 할 듯?
}
