package org.omocha.domain.review;

import org.omocha.domain.auction.Auction;
import org.omocha.domain.common.BaseEntity;
import org.omocha.domain.member.Member;
import org.omocha.domain.review.exception.InvalidReviewTypeException;
import org.omocha.domain.review.rating.Rating;
import org.omocha.domain.review.rating.RatingDbConverter;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "review")
public class Review extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long reviewId;

	@ManyToOne
	@JoinColumn(name = "auction_id")
	private Auction auction;

	@ManyToOne
	@JoinColumn(name = "reviewer_member_id")
	private Member reviewer;

	@ManyToOne
	@JoinColumn(name = "recipient_member_id")
	private Member recipient;

	@Enumerated(EnumType.STRING)
	private ReviewType reviewType;

	@Convert(converter = RatingDbConverter.class)
	private Rating rating;

	private String content;

	@Builder
	public Review(
		Auction auction,
		Member reviewer,
		Member recipient,
		ReviewType reviewType,
		Rating rating,
		String content
	) {
		this.auction = auction;
		this.reviewer = reviewer;
		this.recipient = recipient;
		this.reviewType = reviewType;
		this.rating = rating;
		this.content = content;
	}

	@Getter
	@RequiredArgsConstructor
	public enum ReviewType {
		SELL_REVIEW("판매 리뷰"),
		BUY_REVIEW("구매 리뷰");

		private final String description;

		public static ReviewType fromString(String reviewType) {
			try {
				return ReviewType.valueOf(reviewType);
			} catch (Exception e) {
				throw new InvalidReviewTypeException(reviewType);
			}
		}
	}
}
