package org.auction.client.bid.application;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.auction.client.bid.interfaces.request.CreateBidRequest;
import org.auction.client.bid.interfaces.response.BidResponse;
import org.auction.client.bid.interfaces.response.CreateBidResponse;
import org.auction.client.bid.interfaces.response.NowPriceResponse;
import org.auction.client.common.code.AuctionCode;
import org.auction.client.common.code.BidCode;
import org.auction.client.common.code.MemberCode;
import org.auction.client.exception.auction.AuctionAlreadyEndedException;
import org.auction.client.exception.auction.AuctionIllegalStateException;
import org.auction.client.exception.auction.AuctionNotFoundException;
import org.auction.client.exception.bid.BidIllegalArgumentException;
import org.auction.client.exception.bid.InvalidBidUnitException;
import org.auction.client.exception.bid.SelfBidNotAllowedException;
import org.auction.client.exception.member.MemberNotFoundException;
import org.auction.domain.auction.domain.entity.AuctionEntity;
import org.auction.domain.auction.domain.enums.AuctionStatus;
import org.auction.domain.auction.infrastructure.AuctionRepository;
import org.auction.domain.bid.entity.BidEntity;
import org.auction.domain.bid.infrastructure.BidRepository;
import org.auction.domain.member.domain.entity.MemberEntity;
import org.auction.domain.member.infrastructure.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BidService {

	private final BidRepository bidRepository;
	private final AuctionRepository auctionRepository;
	private final MemberRepository memberRepository;

	// TODO : 동시성 해결 해결 해야 함

	@Transactional(readOnly = true)
	public List<BidResponse> findBidList(
		Long auctionId
	) {

		log.debug("find BidList started for auctionId: {}", auctionId);

		AuctionEntity auctionEntity = auctionRepository.findById(auctionId)
			.orElseThrow(() -> new AuctionNotFoundException(AuctionCode.AUCTION_NOT_FOUND));

		List<BidResponse> bidResponses = bidRepository.findAllByAuctionEntityOrderByCreatedAtDesc(auctionEntity)
			.stream()
			.map(BidResponse::toDto)
			.toList();

		log.debug("find BidList finished for auctionId: {}", auctionId);

		return bidResponses;
	}

	// TODO : 최고 입찰가 관련 논의 후 수정 필요
	@Transactional
	public CreateBidResponse addBid(
		Long auctionId,
		Long buyerId,
		CreateBidRequest createBidRequest
	) {

		log.debug("Add Bid started auctionId : {}, createBidRequest: {}",
			auctionId, createBidRequest);

		Long bidPrice = createBidRequest.bidPrice();

		AuctionEntity auctionEntity = auctionRepository.findById(auctionId)
			.orElseThrow(() -> new AuctionNotFoundException(AuctionCode.AUCTION_NOT_FOUND));

		// TODO : 검증 로직 추후 수정
		validateAuctionStatus(auctionEntity);

		if (auctionEntity.getMemberEntity().getMemberId() == buyerId) {
			throw new SelfBidNotAllowedException(BidCode.SELF_BID_NOT_ALLOWED);
		}

		validateBidPrice(auctionEntity, bidPrice);

		MemberEntity memberEntity = memberRepository.findById(buyerId)
			.orElseThrow(() -> new MemberNotFoundException(MemberCode.MEMBER_NOT_FOUND));

		BidEntity bidEntity = BidEntity.builder()
			.auctionEntity(auctionEntity)
			.buyerEntity(memberEntity)
			.bidPrice(bidPrice)
			.build();

		bidRepository.save(bidEntity);

		updateHighestBidPrice(bidEntity);

		log.debug("Add Bid finished for auctionId: {}, createBidRequest: {}",
			auctionId, createBidRequest);

		return CreateBidResponse.toDto(bidEntity);

	}

	private void validateAuctionStatus(
		AuctionEntity auctionEntity
	) {
		LocalDateTime now = LocalDateTime.now();

		if (auctionEntity.getEndDate().isBefore(now)) {
			throw new AuctionAlreadyEndedException(AuctionCode.AUCTION_ALREADY_ENDED);
		}

		if (auctionEntity.getAuctionStatus() != AuctionStatus.BIDDING) {
			throw new AuctionIllegalStateException(AuctionCode.AUCTION_WRONG_STATUS);
		}
	}

	// EXPLAIN : 가격 검증 메서드
	// 처음 입찰 : 시작가보다 낮은 입찰 가격이 발생할 때 예외
	// 이후 입찰 : 입찰 가격 < 현재 최고가 보다 낮을 때 예외 발생
	// TODO: Transaction을 위해 protected 처리, 추후 리팩토링 필요
	@Transactional(readOnly = true)
	protected void validateBidPrice(
		AuctionEntity auctionEntity,
		Long bidPrice
	) {
		if ((bidPrice - auctionEntity.getStartPrice()) % auctionEntity.getBidUnit() != 0) {
			throw new InvalidBidUnitException(BidCode.INVALID_BID_UNIT);
		}

		Long currentHighestBidPrice = getCurrentHighestBidPrice(auctionEntity.getAuctionId());

		if (bidPrice < auctionEntity.getStartPrice()) {
			throw new BidIllegalArgumentException(BidCode.BID_PRICE_TOO_LOW);
		}

		if (bidPrice <= currentHighestBidPrice) {
			throw new BidIllegalArgumentException(BidCode.BID_PRICE_NOT_HIGHER);
		}
	}

	private void checkBidPrice(Long bidPrice, Long currentPrice, BidCode bidCode) {

	}

	// TODO: 추후 getCurrentHighestBidPrice와 함께 리팩토링 필요
	// EXPLAIN : 현재 최고가 return
	// In-Memory-DB 에 값이 있으면 바로 return, 없으면 DB에 조회에서 return
	@Transactional(readOnly = true)
	public Optional<BidEntity> getCurrentHighestBid(
		Long auctionId
	) {
		return Optional.ofNullable(HighestBid.getHighestBid(auctionId))
			.or(() -> findHighestBid(auctionId));
	}

	private Optional<BidEntity> findHighestBid(
		Long auctionId
	) {
		AuctionEntity auction = auctionRepository.findById(auctionId)
			.orElseThrow(() -> new AuctionNotFoundException(AuctionCode.AUCTION_NOT_FOUND));

		return bidRepository.findTopByAuctionEntityOrderByBidPriceDesc(auction);
	}

	@Transactional(readOnly = true)
	public Long getCurrentHighestBidPrice(
		Long auctionId
	) {
		return getCurrentHighestBid(auctionId)
			.map(BidEntity::getBidPrice)
			.orElse(0L);
	}

	private void updateHighestBidPrice(
		BidEntity bidEntity
	) {
		Long auctionId = bidEntity.getAuctionEntity().getAuctionId();

		HighestBid.setHighestBid(auctionId, bidEntity);
	}

	@Transactional(readOnly = true)
	public Long findBidCount(AuctionEntity auctionEntity) {
		log.debug("Counting bids for auctionId: {}", auctionEntity.getAuctionId());

		return bidRepository.countByAuctionEntity(auctionEntity);
	}

	public NowPriceResponse findNowPrice(Long auctionId) {
		return findHighestBid(auctionId)
			.map(NowPriceResponse::toDto)
			.orElseGet(() -> new NowPriceResponse(0L, null, LocalDateTime.now()));
	}
}