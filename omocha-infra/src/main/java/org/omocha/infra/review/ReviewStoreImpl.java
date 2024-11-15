package org.omocha.infra.review;

import org.omocha.domain.review.Review;
import org.omocha.domain.review.ReviewStore;
import org.omocha.infra.review.repository.ReviewRepository;
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
