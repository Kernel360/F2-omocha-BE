package org.auction.client.bid.application;

import java.util.Comparator;
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
		Long auction_id
	) {

		AuctionEntity auctionEntity = auctionRepository.findById(auction_id).orElseThrow();

		return bidRepository.findALlByAuctionEntity(auctionEntity).stream()
			.sorted(Comparator.comparing(BidEntity::getCreatedAt).reversed())
			.map(BidResponse::toDto)
			.toList();

	}

	@Transactional
	public CreateBidResponse addBid(
		Long auction_id,
		Long buyerId,
		CreateBidRequest createBidRequest
	) {

		MemberEntity memberEntity = memberRepository.findById(buyerId)
			.orElseThrow(() -> new RuntimeException("Member not found"));

		AuctionEntity auctionEntity = auctionRepository.findById(auction_id)
			.orElseThrow(() -> new RuntimeException("Auction not found"));

		if (!checkLastBid(memberEntity, createBidRequest.getBidPrice())) {
			throw new RuntimeException("Last bid price is incorrect");
		}

		if (!checkAutionStatus(auctionEntity)) {
			throw new RuntimeException("Auction status is incorrect");
		}

		BidEntity bidEntity = BidEntity.builder()
			.auctionEntity(auctionEntity)
			.memberEntity(memberEntity)
			.bidPrice(createBidRequest.getBidPrice())
			.build();

		bidRepository.save(bidEntity);

		return CreateBidResponse.toDto(bidEntity);

	}

	private boolean checkLastBid(
		MemberEntity memberEntity,
		Long bidPrice
	) {
		BidEntity beforeBidEntity = bidRepository.findTopByMemberEntityOrderByBidPriceDesc(memberEntity);

		if (beforeBidEntity != null) {
			return beforeBidEntity.getBidPrice() < bidPrice;
		}
		return true;

	}

	private boolean checkAutionStatus(AuctionEntity auctionEntity) {

		if (!(auctionEntity.getAuctionStatus() == AuctionStatus.BIDDING)) {
			return false;
		}
		return true;

	}

}
