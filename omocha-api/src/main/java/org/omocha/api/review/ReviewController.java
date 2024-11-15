package org.omocha.api.review;

import static org.omocha.domain.common.code.SuccessCode.*;

import org.omocha.api.auth.jwt.UserPrincipal;
import org.omocha.api.common.response.ResultDto;
import org.omocha.api.review.dto.ReviewDto;
import org.omocha.api.review.dto.ReviewDtoMapper;
import org.omocha.domain.common.util.PageSort;
import org.omocha.domain.review.ReviewCommand;
import org.omocha.domain.review.ReviewInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/reviews")
public class ReviewController implements ReviewApi {

	private final ReviewFacade reviewFacade;
	private final ReviewDtoMapper reviewDtoMapper;
	private final PageSort pageSort;

	@Override
	@PostMapping("/{auction_id}")
	public ResponseEntity<ResultDto<ReviewDto.ReviewAddResponse>> reviewAdd(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@PathVariable("auction_id") Long auctionId,
		@RequestBody ReviewDto.ReviewAddRequest request
	) {
		ReviewCommand.AddReview addReview = reviewDtoMapper.toCommand(
			auctionId,
			userPrincipal.getId(),
			request
		);

		Long reviewId = reviewFacade.addReview(addReview);
		ReviewDto.ReviewAddResponse response = reviewDtoMapper.toResponse(reviewId);

		ResultDto<ReviewDto.ReviewAddResponse> resultDto = ResultDto.res(
			REVIEW_ADD_SUCCESS.getStatusCode(),
			REVIEW_ADD_SUCCESS.getDescription(),
			response
		);

		return ResponseEntity
			.status(REVIEW_ADD_SUCCESS.getHttpStatus())
			.body(resultDto);
	}

	@Override
	@GetMapping("/received/{member_id}")
	public ResponseEntity<ResultDto<Page<ReviewDto.ReceivedReviewListResponse>>> memberReceivedReviewList(
		@PathVariable("member_id") Long memberId,
		@RequestParam(value = "sort", defaultValue = "createdAt") String sort,
		@RequestParam(value = "direction", defaultValue = "DESC") String direction,
		@PageableDefault(page = 0, size = 10)
		Pageable pageable
	) {
		Pageable sortPage = pageSort.sortPage(pageable, sort, direction);

		ReviewCommand.RetrieveReviews reviewsCommand = reviewDtoMapper.toCommand(memberId);

		Page<ReviewInfo.RetrieveReviews> retrieveReceivedReviews = reviewFacade.retrieveReceivedReviews(
			reviewsCommand,
			sortPage
		);

		Page<ReviewDto.ReceivedReviewListResponse> response = reviewDtoMapper.toReceivedReviewListResponse(
			retrieveReceivedReviews
		);

		ResultDto<Page<ReviewDto.ReceivedReviewListResponse>> resultDto = ResultDto.res(
			REVIEW_LIST_ACCESS_SUCCESS.getStatusCode(),
			REVIEW_LIST_ACCESS_SUCCESS.getDescription(),
			response
		);

		return ResponseEntity
			.status(REVIEW_LIST_ACCESS_SUCCESS.getHttpStatus())
			.body(resultDto);
	}

	@Override
	@GetMapping("/received")
	public ResponseEntity<ResultDto<Page<ReviewDto.ReceivedReviewListResponse>>> myReceivedReviewList(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@RequestParam(value = "sort", defaultValue = "createdAt") String sort,
		@RequestParam(value = "direction", defaultValue = "DESC") String direction,
		@PageableDefault(page = 0, size = 10)
		Pageable pageable
	) {
		Pageable sortPage = pageSort.sortPage(pageable, sort, direction);

		ReviewCommand.RetrieveReviews reviewsCommand = reviewDtoMapper.toCommand(userPrincipal.getId());

		Page<ReviewInfo.RetrieveReviews> retrieveReceivedReviews = reviewFacade.retrieveReceivedReviews(
			reviewsCommand,
			sortPage
		);

		Page<ReviewDto.ReceivedReviewListResponse> response = reviewDtoMapper.toReceivedReviewListResponse(
			retrieveReceivedReviews
		);

		ResultDto<Page<ReviewDto.ReceivedReviewListResponse>> resultDto = ResultDto.res(
			REVIEW_LIST_ACCESS_SUCCESS.getStatusCode(),
			REVIEW_LIST_ACCESS_SUCCESS.getDescription(),
			response
		);

		return ResponseEntity
			.status(REVIEW_LIST_ACCESS_SUCCESS.getHttpStatus())
			.body(resultDto);
	}

	@Override
	@GetMapping("/given")
	public ResponseEntity<ResultDto<Page<ReviewDto.GivenReviewListResponse>>> myGivenReviewList(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@RequestParam(value = "sort", defaultValue = "createdAt") String sort,
		@RequestParam(value = "direction", defaultValue = "DESC") String direction,
		@PageableDefault(page = 0, size = 10)
		Pageable pageable
	) {
		Pageable sortPage = pageSort.sortPage(pageable, sort, direction);

		ReviewCommand.RetrieveReviews reviewsCommand = reviewDtoMapper.toCommand(userPrincipal.getId());

		Page<ReviewInfo.RetrieveReviews> retrieveGivenReviews = reviewFacade.retrieveGivenReviews(
			reviewsCommand,
			sortPage
		);

		Page<ReviewDto.GivenReviewListResponse> response = reviewDtoMapper.toGivenReviewListResponse(
			retrieveGivenReviews
		);

		ResultDto<Page<ReviewDto.GivenReviewListResponse>> resultDto = ResultDto.res(
			REVIEW_LIST_ACCESS_SUCCESS.getStatusCode(),
			REVIEW_LIST_ACCESS_SUCCESS.getDescription(),
			response
		);

		return ResponseEntity
			.status(REVIEW_LIST_ACCESS_SUCCESS.getHttpStatus())
			.body(resultDto);
	}
}
