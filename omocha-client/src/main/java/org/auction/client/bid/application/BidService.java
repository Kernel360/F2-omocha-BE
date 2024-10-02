package org.auction.client.bid.application;

import java.util.List;

import org.auction.client.bid.interfaces.request.CreateBidRequest;
import org.auction.client.bid.interfaces.response.BidResponse;
import org.auction.client.bid.interfaces.response.CreateBidResponse;
import org.auction.domain.auction.domain.entity.AuctionEntity;
import org.auction.domain.auction.domain.enums.AuctionStatus;
import org.auction.domain.auction.infrastructure.AuctionRepository;
import org.auction.domain.bid.entity.BidEntity;
import org.auction.domain.bid.infrastructure.BidRepository;
import org.auction.domain.user.domain.entity.MemberEntity;
import org.auction.domain.user.infrastructure.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BidService {

	private final BidRepository bidRepository;
	private final AuctionRepository auctionRepository;
	private final MemberRepository memberRepository;

	@Transactional(readOnly = true)
	public List<BidResponse> findBidList(
		Long auctionId
	) {

		AuctionEntity auctionEntity = auctionRepository.findById(auctionId)
			.orElseThrow(() -> new RuntimeException("Auction not found"));

		return bidRepository.findAllByAuctionEntityOrderByCreatedAtDesc(auctionEntity)
			.stream()
			.map(BidResponse::toDto)
			.toList();

	}

	// TODO : 최고 입찰가 관련 논의 후 수정 필요
	// TODO : Log, Error 처리 필요
	@Transactional
	public CreateBidResponse addBid(
		Long auctionId,
		Long buyerId,
		CreateBidRequest createBidRequest
	) {

		MemberEntity memberEntity = memberRepository.findById(buyerId)
			.orElseThrow(() -> new RuntimeException("Member not found"));

		AuctionEntity auctionEntity = auctionRepository.findById(auctionId)
			.orElseThrow(() -> new RuntimeException("Auction not found"));

		if (!checkLastBidPrice(memberEntity, createBidRequest.bidPrice())) {
			throw new RuntimeException("Last bid price is incorrect");
		}

		if (!checkAuctionStatus(auctionEntity)) {
			throw new RuntimeException("Auction status is incorrect");
		}

		if (!checkLastBiddingMember(memberEntity)) {
			throw new RuntimeException("Last bidding member is incorrect");
		}

		BidEntity bidEntity = BidEntity.builder()
			.auctionEntity(auctionEntity)
			.memberEntity(memberEntity)
			.bidPrice(createBidRequest.bidPrice())
			.build();

		bidRepository.save(bidEntity);

		return CreateBidResponse.toDto(bidEntity);

	}

	private boolean checkLastBidPrice(
		MemberEntity memberEntity,
		Long bidPrice
	) {

		return bidRepository.findTopByMemberEntityOrderByBidPriceDesc(memberEntity)
			.map(bid -> bid.getBidPrice() < bidPrice)
			.orElse(true);

	}

	private boolean checkAuctionStatus(
		AuctionEntity auctionEntity
	) {

		return auctionEntity.getAuctionStatus() == AuctionStatus.BIDDING;

	}

	// TODO : 재입찰 논의 후 수정
	private boolean checkLastBiddingMember(
		MemberEntity memberEntity
	) {

		return bidRepository.findTopByMemberEntityOrderByCreatedAtDesc(memberEntity)
			.map(entity -> entity.getMemberEntity().getMemberId().equals(memberEntity.getMemberId()))
			.orElse(true);

	}

}
