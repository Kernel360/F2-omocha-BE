package org.omocha.infra.review;

import org.omocha.domain.review.rating.Rating;
import org.omocha.domain.review.ReviewCommand;
import org.omocha.domain.review.ReviewInfo;
import org.omocha.domain.review.ReviewReader;
import org.omocha.infra.review.repository.ReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReviewReaderImpl implements ReviewReader {

	private final ReviewRepository reviewRepository;

	@Override
	public boolean checkExistReview(Long auctionId, Long memberId) {
		return reviewRepository.existsByAuctionAuctionIdAndReviewerMemberId(auctionId, memberId);
	}

	@Override
	public Page<ReviewInfo.RetrieveReviews> getReceivedReviews(
		ReviewCommand.RetrieveReviews retrieveReviews,
		Pageable pageable
	) {
		return reviewRepository.getReceivedReviews(retrieveReviews, pageable);
	}

	@Override
	public Page<ReviewInfo.RetrieveReviews> getGivenReviews(
		ReviewCommand.RetrieveReviews retrieveReviews,
		Pageable pageable
	) {
		return reviewRepository.getGivenReviews(retrieveReviews, pageable);
	}

	@Override
	public Rating getAverageRating(Long recipientId) {
		return new Rating(reviewRepository.findAverageRatingByRecipientId(recipientId));
	}
}
