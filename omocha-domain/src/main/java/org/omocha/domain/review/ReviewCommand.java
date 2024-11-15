package org.omocha.domain.review;

import org.omocha.domain.auction.Auction;
import org.omocha.domain.member.Member;
import org.omocha.domain.review.rating.Rating;

public class ReviewCommand {

	public record AddReview(
		Long auctionId,
		Long reviewerId,
		Review.ReviewType reviewType,
		Rating rating,
		String content
	) {
		public Review toEntity(
			Auction auction,
			Member reviewer,
			Member recipient
		) {
			return Review.builder()
				.auction(auction)
				.reviewer(reviewer)
				.recipient(recipient)
				.reviewType(this.reviewType)
				.rating(this.rating)
				.content(this.content)
				.build();
		}
	}

	public record RetrieveReviews(
		Long memberId
	) {

	}
}
