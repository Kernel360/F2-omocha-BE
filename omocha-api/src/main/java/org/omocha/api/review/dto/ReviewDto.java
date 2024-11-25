package org.omocha.api.review.dto;

import java.time.LocalDateTime;

import org.omocha.domain.review.Review;
import org.omocha.domain.review.rating.Rating;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ReviewDto {
	public record ReviewAddRequest(
		@NotBlank String reviewType,
		@NotNull Double rating,
		@NotBlank String content
	) {

	}

	public record ReviewAddResponse(
		Long reviewId
	) {
	}

	public record ReceivedReviewListResponse(
		Long reviewerMemberId,
		String reviewerMemberNickname,
		Long auctionId,
		String auctionTitle,
		String thumbnailPath,
		Review.ReviewType reviewType,
		Rating rating,
		String content,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime createAt
	) {
	}

	public record GivenReviewListResponse(
		Long recipientMemberId,
		String recipientMemberNickname,
		Long auctionId,
		String auctionTitle,
		String thumbnailPath,
		Review.ReviewType reviewType,
		Rating rating,
		String content,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime createAt
	) {
	}
}
