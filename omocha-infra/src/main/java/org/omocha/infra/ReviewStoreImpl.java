package org.omocha.infra;

import org.omocha.domain.auction.review.Review;
import org.omocha.domain.auction.review.ReviewStore;
import org.omocha.infra.repository.ReviewRepository;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReviewStoreImpl implements ReviewStore {

	private final ReviewRepository reviewRepository;

	@Override
	public Review addReview(Review review) {
		return reviewRepository.save(review);
	}
}
