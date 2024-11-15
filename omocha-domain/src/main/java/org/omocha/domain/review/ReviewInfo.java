package org.omocha.domain.review;

import java.time.LocalDateTime;

import org.omocha.domain.review.rating.Rating;

import com.querydsl.core.annotations.QueryProjection;

public class ReviewInfo {

	public record RetrieveReviews(
		Long memberId,
		String nickname,
		Long auctionId,
		String auctionTitle,
		String auctionThumbnailPath,
		Review.ReviewType reviewType,
		Rating rating,
		String content,
		LocalDateTime createAt
	) {
		@QueryProjection
		public RetrieveReviews(
			Long memberId,
			String nickname,
			Long auctionId,
			String auctionTitle,
			String auctionThumbnailPath,
			Review.ReviewType reviewType,
			Rating rating,
			String content,
			LocalDateTime createAt
		) {
			this.memberId = memberId;
			this.nickname = nickname;
			this.auctionId = auctionId;
			this.auctionTitle = auctionTitle;
			this.auctionThumbnailPath = auctionThumbnailPath;
			this.reviewType = reviewType;
			this.rating = rating;
			this.content = content;
			this.createAt = createAt;
		}
	}
}
