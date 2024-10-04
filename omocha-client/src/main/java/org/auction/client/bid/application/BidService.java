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

		log.debug("find BidList started : {}", auctionId);

		AuctionEntity auctionEntity = auctionRepository.findById(auctionId)
			.orElseThrow(() -> new AuctionNotFoundException(AuctionCode.AUCTION_NOT_FOUND));

		log.debug("find BidList finished : {}", auctionId);

		return bidRepository.findAllByAuctionEntityOrderByCreatedAtDesc(auctionEntity)
			.stream()
			.map(BidResponse::toDto)
			.toList();

	}

	// TODO : 최고 입찰가 관련 논의 후 수정 필요
	@Transactional
	public CreateBidResponse addBid(
		Long auctionId,
		Long buyerId,
		CreateBidRequest createBidRequest
	) {

		log.debug("Add Bid started auctionId : {} ", auctionId);
		log.debug("Add Bid started createBidRequest : {} ", createBidRequest);

		MemberEntity memberEntity = memberRepository.findById(buyerId)
			.orElseThrow(() -> new MemberNotFoundException(MemberCode.MEMBER_NOT_FOUND));

		AuctionEntity auctionEntity = auctionRepository.findById(auctionId)
			.orElseThrow(() -> new AuctionNotFoundException(AuctionCode.AUCTION_NOT_FOUND));

		// TODO : 검증 로직 추후 수정
		validateAuctionBidStatus(auctionEntity);

		validateBidPriceHigherThanStartPrice(auctionEntity, createBidRequest.bidPrice());

		isCurrentBidHigherThanLastBid(auctionEntity, createBidRequest.bidPrice());

		BidEntity bidEntity = BidEntity.builder()
			.auctionEntity(auctionEntity)
			.memberEntity(memberEntity)
			.bidPrice(createBidRequest.bidPrice())
			.build();

		bidRepository.save(bidEntity);

		updateHashMap(bidEntity);

		log.debug("Add Bid finished auctionId : {} ", auctionId);
		log.debug("Add Bid finished createBidRequest : {} ", createBidRequest);

		return CreateBidResponse.toDto(bidEntity);

	}

	private void isCurrentBidHigherThanLastBid(
		AuctionEntity auctionEntity,
		Long bidPrice
	) {

		if (HighestBid.hasHighestBid(auctionEntity.getAuctionId())) {

			validateBidPrice(auctionEntity, bidPrice);

		} else {

			bidRepository.findTopByAuctionEntityOrderByBidPriceDesc(auctionEntity)
				.map(bid -> {
					if (bid.getBidPrice() < bidPrice) {
						return true; // 조건이 만족되면 true 반환
					} else {
						throw new BidIllegalArgumentException(BidCode.BIDPRICE_BELOW_HIGHESTBID); // 조건이 불만족할 경우 예외 발생
					}
				});
		}

	}

	private void validateAuctionBidStatus(
		AuctionEntity auctionEntity
	) {

		if (auctionEntity.getAuctionStatus() != AuctionStatus.BIDDING) {
			throw new AuctionIllegalStateException(AuctionCode.AUCTION_WRONG_STATUS);
		}
	}

	private void updateHashMap(
		BidEntity bidEntity
	) {
		HighestBid.setHighestBid(bidEntity.getAuctionEntity().getAuctionId(), bidEntity.getBidPrice());
	}

	private void validateBidPrice(
		AuctionEntity auctionEntity,
		Long bidPrice
	) {

		if (HighestBid.getHighestBid(auctionEntity.getAuctionId()) >= bidPrice) {
			throw new BidIllegalArgumentException(BidCode.BIDPRICE_BELOW_HIGHESTBID);
		}

	}

	private void validateBidPriceHigherThanStartPrice(
		AuctionEntity auctionEntity,
		Long bidPrice
	) {

		if (auctionEntity.getStartPrice() > bidPrice) {
			throw new BidIllegalArgumentException(BidCode.BIDPRICE_BELOW_STARTPRICE);
		}

	}

}
