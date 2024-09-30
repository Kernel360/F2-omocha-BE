package org.auction.client.bid.application;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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

		AuctionEntity auctionEntity = auctionRepository.findById(auctionId).orElseThrow();

		// TODO : 멘토링 이후 수정 필요
		return bidRepository.findALlByAuctionEntity(auctionEntity).stream()
			.sorted(Comparator.comparing(BidEntity::getCreatedAt).reversed())
			.map(BidResponse::toDto)
			.toList();

	}

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

		if (!checkLastBid(memberEntity, createBidRequest.getBidPrice())) {
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
			.bidPrice(createBidRequest.getBidPrice())
			.build();

		bidRepository.save(bidEntity);

		return CreateBidResponse.toDto(bidEntity);

	}

	private boolean checkLastBid(
		MemberEntity memberEntity,
		Long bidPrice
	) {
		Optional<BidEntity> beforeBidEntity = bidRepository.findTopByMemberEntityOrderByBidPriceDesc(memberEntity);

		if (beforeBidEntity.isPresent()) {
			return beforeBidEntity.get().getBidPrice() < bidPrice;
		}
		return true;

	}

	private boolean checkAuctionStatus(
		AuctionEntity auctionEntity
	) {

		if (!(auctionEntity.getAuctionStatus() == AuctionStatus.BIDDING)) {
			return false;
		}
		return true;

	}

	private boolean checkLastBiddingMember(
		MemberEntity memberEntity
	) {

		Optional<BidEntity> bidEntity = bidRepository.findTopByMemberEntityOrderByCreatedAtDesc(memberEntity);

		if (!(bidEntity.get().getMemberEntity().getMemberId().equals(memberEntity.getMemberId()))) {
			return false;
		}

		return true;

	}

}
