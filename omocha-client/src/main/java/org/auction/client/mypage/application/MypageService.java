package org.auction.client.mypage.application;

import java.util.List;
import java.util.stream.Collectors;

import org.auction.client.bid.application.BidService;
import org.auction.client.common.code.MemberCode;
import org.auction.client.exception.member.InvalidMemberException;
import org.auction.client.exception.member.MemberNotFoundException;
import org.auction.client.mypage.interfaces.response.MemberInfoResponse;
import org.auction.client.mypage.interfaces.response.MypageAuctionListResponse;
import org.auction.client.mypage.interfaces.response.MypageBidListResponse;
import org.auction.domain.auction.domain.entity.AuctionEntity;
import org.auction.domain.auction.domain.enums.AuctionStatus;
import org.auction.domain.auction.infrastructure.AuctionRepository;
import org.auction.domain.bid.entity.BidEntity;
import org.auction.domain.bid.infrastructure.BidRepository;
import org.auction.domain.image.domain.entity.ImageEntity;
import org.auction.domain.member.domain.entity.MemberEntity;
import org.auction.domain.member.infrastructure.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MypageService {

	private final AuctionRepository auctionRepository;
	private final MemberRepository memberRepository;
	private final BidRepository bidRepository;
	private final BidService bidService;

	public MemberInfoResponse findMe(
		Long memberId
	) {

		log.debug("find me start for member {}", memberId);

		MemberEntity memberEntity = memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberNotFoundException(MemberCode.MEMBER_NOT_FOUND));

		if (!memberEntity.getMemberId().equals(memberId)) {
			throw new InvalidMemberException(MemberCode.INVALID_MEMBER);
		}

		MemberInfoResponse memberInfoResponse = MemberInfoResponse.toDto(memberEntity);

		log.debug("find me finished for member {}", memberId);

		return memberInfoResponse;

	}

	@Transactional(readOnly = true)
	public Page<MypageAuctionListResponse> findTransactionAuctionList(
		Long memberId,
		AuctionStatus auctionStatus,
		Pageable pageable
	) {

		log.debug("find transaction auction list start for member {}", memberId);

		MemberEntity memberEntity = memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberNotFoundException(MemberCode.MEMBER_NOT_FOUND));

		Page<AuctionEntity> auctionPageList = auctionRepository.searchMyAuctionList(memberEntity.getMemberId(),
			auctionStatus,
			pageable);

		// DTO로 변환
		Page<MypageAuctionListResponse> content = auctionPageList.map(auction -> {
			List<String> imageKeys = auction.getImages().stream()
				.map(ImageEntity::getS3Key)
				.collect(Collectors.toList());
			return new MypageAuctionListResponse(
				auction.getAuctionId(),
				auction.getTitle(),
				auction.getAuctionStatus(),
				bidService.getCurrentHighestBidPrice(auction),
				auction.getEndDate(),
				imageKeys
			);

		});

		log.debug("get transaction auction list finish for member {}", memberId);

		return content;
	}

	@Transactional(readOnly = true)
	public Page<MypageBidListResponse> findTransactionBidList(
		Long memberId,
		Pageable pageable
	) {

		log.debug("get transaction bid list start for member {}", memberId);

		MemberEntity memberEntity = memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberNotFoundException(MemberCode.MEMBER_NOT_FOUND));

		Page<BidEntity> bidPageList = bidRepository.searchMyBidList(memberEntity.getMemberId(), pageable);

		Page<MypageBidListResponse> content = bidPageList.map(auction -> {
			List<String> imageKeys = auction.getAuctionEntity().getImages().stream()
				.map(ImageEntity::getS3Key)
				.collect(Collectors.toList());
			return new MypageBidListResponse(
				auction.getAuctionEntity().getAuctionId(),
				auction.getAuctionEntity().getTitle(),
				auction.getBidPrice(),
				auction.getAuctionEntity().getEndDate(),
				imageKeys
			);

		});

		log.debug("get transaction bid finish list for member {}", memberId);

		return content;

	}
}
