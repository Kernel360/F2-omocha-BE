package org.omocha.infra;

import org.omocha.domain.auction.review.Rating;
import org.omocha.domain.auction.review.ReviewCommand;
import org.omocha.domain.auction.review.ReviewInfo;
import org.omocha.domain.auction.review.ReviewReader;
import org.omocha.infra.repository.ReviewRepository;
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
	public Page<ReviewInfo.RetrieveReviews> getReviews(
		ReviewCommand.RetrieveReviews retrieveReviews,
		Pageable pageable
	) {
		return reviewRepository.getReceivedReviews(retrieveReviews, pageable);
	}

	@Override
	public Rating getAverageRating(Long recipientId) {
		return new Rating(reviewRepository.findAverageRatingByRecipientId(recipientId));
	}
}
