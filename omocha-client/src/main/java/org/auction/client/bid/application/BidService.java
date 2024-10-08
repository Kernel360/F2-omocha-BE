package org.auction.client.bid.application;

import java.util.List;

import org.auction.client.bid.interfaces.request.CreateBidRequest;
import org.auction.client.bid.interfaces.response.BidResponse;
import org.auction.client.bid.interfaces.response.CreateBidResponse;
import org.auction.client.common.code.AuctionCode;
import org.auction.client.common.code.BidCode;
import org.auction.client.common.code.MemberCode;
import org.auction.client.exception.auction.AuctionIllegalStateException;
import org.auction.client.exception.auction.AuctionNotFoundException;
import org.auction.client.exception.bid.BidIllegalArgumentException;
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

		MemberEntity memberEntity = memberRepository.findById(buyerId)
			.orElseThrow(() -> new MemberNotFoundException(MemberCode.MEMBER_NOT_FOUND));

		AuctionEntity auctionEntity = auctionRepository.findById(auctionId)
			.orElseThrow(() -> new AuctionNotFoundException(AuctionCode.AUCTION_NOT_FOUND));

		// TODO : 검증 로직 추후 수정
		validateAuctionStatus(auctionEntity);

		validateBidPrice(auctionEntity, createBidRequest.bidPrice());

		BidEntity bidEntity = BidEntity.builder()
			.auctionEntity(auctionEntity)
			.memberEntity(memberEntity)
			.bidPrice(createBidRequest.bidPrice())
			.build();

		bidRepository.save(bidEntity);

		updateHighestBidPrice(bidEntity);

		log.debug("addBid finished for auctionId: {}, createBidRequest: {}",
			auctionId, createBidRequest);

		return CreateBidResponse.toDto(bidEntity);

	}

	private void validateAuctionStatus(
		AuctionEntity auctionEntity
	) {

		if (auctionEntity.getAuctionStatus() != AuctionStatus.BIDDING) {
			throw new AuctionIllegalStateException(AuctionCode.AUCTION_WRONG_STATUS);
		}
	}

	// EXPLAIN : 가격 검증 메서드
	// 처음 입찰 : 현재가보다 낮은 입찰 가격이 발생할 때 예외
	// 이후 입찰 : 입찰 가격 < 현재 최고가 보다 낮을 때 예외 발생
	private void validateBidPrice(
		AuctionEntity auctionEntity,
		Long bidPrice
	) {
		Long currentHighestBidPrice = getCurrentHighestBidPrice(auctionEntity);

		if (currentHighestBidPrice != null) {
			if (bidPrice <= currentHighestBidPrice) {
				throw new BidIllegalArgumentException(BidCode.BIDPRICE_BELOW_HIGHESTBID);
			}
		} else {
			if (bidPrice < auctionEntity.getStartPrice()) {
				throw new BidIllegalArgumentException(BidCode.BIDPRICE_BELOW_STARTPRICE);
			}
		}
	}

	// EXPLAIN : 현재 최고가 return
	// In-Memory-DB 에 값이 있으면 바로 return, 없으면 DB에 조회에서 return, DB에도 없으면 현재가를 return
	public Long getCurrentHighestBidPrice(
		AuctionEntity auctionEntity
	) {
		Long auctionId = auctionEntity.getAuctionId();

		if (HighestBid.hasHighestBid(auctionId)) {
			return HighestBid.getHighestBid(auctionId);
		} else {
			return bidRepository.findTopByAuctionEntityOrderByBidPriceDesc(auctionEntity)
				.map(BidEntity::getBidPrice)
				.orElse(null);
		}

	}

	private void updateHighestBidPrice(
		BidEntity bidEntity
	) {
		Long auctionId = bidEntity.getAuctionEntity().getAuctionId();
		Long bidPrice = bidEntity.getBidPrice();

		HighestBid.setHighestBid(auctionId, bidPrice);
	}
}