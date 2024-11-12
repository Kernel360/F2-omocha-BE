package org.omocha.api.interfaces.dto;

import java.time.LocalDateTime;

import org.omocha.domain.auction.review.Rating;
import org.omocha.domain.auction.review.Review;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ReviewDto {
	public record ReviewAddRequest(
		String reviewType,
		Double rating,
		String content
	) {

	}

	public record ReviewAddResponse(
		Long reviewId
	) {

	}

	public record ReviewListRequest(
		Long memberId,
		String category
	) {

	}

	public record ReviewListResponse(
		Long memberId,
		String memberNickname,
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
