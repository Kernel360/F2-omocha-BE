package org.omocha.api.review.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.omocha.api.common.util.ValueObjectMapper;
import org.omocha.domain.review.Review;
import org.omocha.domain.review.ReviewCommand;
import org.omocha.domain.review.ReviewInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

@Mapper(
	componentModel = "spring",
	injectionStrategy = InjectionStrategy.CONSTRUCTOR,
	unmappedTargetPolicy = ReportingPolicy.ERROR,
	uses = ValueObjectMapper.class
)
public interface ReviewDtoMapper {

	@Mapping(target = "reviewType", source = "request.reviewType", qualifiedByName = "toReviewType")
	@Mapping(target = "rating", source = "request.rating", qualifiedByName = "toRating")
	ReviewCommand.AddReview toCommand(
		Long auctionId,
		Long reviewerId,
		ReviewDto.ReviewAddRequest request
	);

	@Named("toReviewType")
	default Review.ReviewType toReviewType(String reviewType) {
		return Review.ReviewType.fromString(reviewType);
	}

	ReviewDto.ReviewAddResponse toResponse(Long reviewId);

	ReviewCommand.RetrieveReviews toCommand(Long memberId);

	default Page<ReviewDto.ReceivedReviewListResponse> toReceivedReviewListResponse(
		Page<ReviewInfo.RetrieveReviews> userReviews
	) {
		List<ReviewDto.ReceivedReviewListResponse> content = userReviews.getContent().stream()
			.map(this::toReceivedReviewResponse)
			.collect(Collectors.toList());

		return new PageImpl<>(content, userReviews.getPageable(), userReviews.getTotalElements());
	}

	@Mapping(target = "thumbnailPath", source = "reviewInfo.auctionThumbnailPath")
	@Mapping(target = "reviewerMemberId", source = "reviewInfo.memberId")
	@Mapping(target = "reviewerMemberNickname", source = "reviewInfo.nickname")
	ReviewDto.ReceivedReviewListResponse toReceivedReviewResponse(ReviewInfo.RetrieveReviews reviewInfo);

	default Page<ReviewDto.GivenReviewListResponse> toGivenReviewListResponse(
		Page<ReviewInfo.RetrieveReviews> userReviews) {
		List<ReviewDto.GivenReviewListResponse> content = userReviews.getContent().stream()
			.map(this::toGivenReviewResponse)
			.collect(Collectors.toList());

		return new PageImpl<>(content, userReviews.getPageable(), userReviews.getTotalElements());
	}

	@Mapping(target = "thumbnailPath", source = "reviewInfo.auctionThumbnailPath")
	@Mapping(target = "recipientMemberId", source = "reviewInfo.memberId")
	@Mapping(target = "recipientMemberNickname", source = "reviewInfo.nickname")
	ReviewDto.GivenReviewListResponse toGivenReviewResponse(ReviewInfo.RetrieveReviews reviewInfo);
}
