package org.omocha.domain.review;

import org.omocha.domain.auction.Auction;
import org.omocha.domain.auction.AuctionReader;
import org.omocha.domain.conclude.Conclude;
import org.omocha.domain.conclude.ConcludeReader;
import org.omocha.domain.member.Member;
import org.omocha.domain.member.MemberReader;
import org.omocha.domain.review.exception.ReviewAlreadyExistException;
import org.omocha.domain.review.exception.ReviewPermissionDeniedException;
import org.omocha.domain.review.rating.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

	private final ReviewReader reviewReader;
	private final ReviewStore reviewStore;
	private final AuctionReader auctionReader;
	private final ConcludeReader concludeReader;
	private final MemberReader memberReader;

	@Override
	@Transactional
	public Long addReview(ReviewCommand.AddReview addReview) {
		Long auctionId = addReview.auctionId();
		Long reviewerId = addReview.reviewerId();

		validateNoExistingReview(auctionId, reviewerId);
		Auction auction = validateAuctionStatus(auctionId);

		Long recipientId = determineRecipientId(reviewerId, auction);
		Review review = createReviewEntity(addReview, auction, reviewerId, recipientId);

		Long reviewId = reviewStore.addReview(review).getReviewId();

		// Member 평점 업데이트 필요
		// 그런데 평점을 업데이트 하려면 count 해서 평균내고 계산을 해서 넣어줘야함

		Member recipient = memberReader.getMember(recipientId);
		Rating averageRating = reviewReader.getAverageRating(recipientId);

		recipient.updateAverageRating(averageRating);

		return reviewId;
	}

	private void validateNoExistingReview(Long auctionId, Long reviewerId) {
		if (reviewReader.checkExistReview(auctionId, reviewerId)) {
			throw new ReviewAlreadyExistException(auctionId, reviewerId);
		}
	}

	private Auction validateAuctionStatus(Long auctionId) {
		Auction auction = auctionReader.getAuction(auctionId);
		auction.validateAuctionStatusConcludedOrCompleted();
		return auction;
	}

	private Long determineRecipientId(Long reviewerId, Auction auction) {
		Conclude conclude = concludeReader.getConclude(auction.getAuctionId());
		Long sellerMemberId = auction.getMemberId();
		Long buyerMemberId = conclude.getBuyer().getMemberId();

		if (reviewerId.equals(sellerMemberId)) {
			return buyerMemberId;
		}

		if (reviewerId.equals(buyerMemberId)) {
			return sellerMemberId;
		}

		throw new ReviewPermissionDeniedException(auction.getAuctionId(), reviewerId);
	}

	private Review createReviewEntity(
		ReviewCommand.AddReview addReview,
		Auction auction,
		Long reviewerId,
		Long recipientId
	) {
		Member reviewer = memberReader.getMember(reviewerId);
		Member recipient = memberReader.getMember(recipientId);
		return addReview.toEntity(auction, reviewer, recipient);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<ReviewInfo.RetrieveReviews> retrieveReceivedReviews(
		ReviewCommand.RetrieveReviews retrieveReviews,
		Pageable pageable
	) {
		return reviewReader.getReceivedReviews(retrieveReviews, pageable);
	}

	@Override
	public Page<ReviewInfo.RetrieveReviews> retrieveGivenReviews(
		ReviewCommand.RetrieveReviews retrieveReviews,
		Pageable pageable
	) {
		return reviewReader.getGivenReviews(retrieveReviews, pageable);
	}
}
