package org.omocha.domain.member.mypage;

import org.omocha.domain.auction.AuctionReader;
import org.omocha.domain.member.Member;
import org.omocha.domain.member.MemberReader;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MypageServiceImpl implements MypageService {

	// TODO : MypageService 를 Admin과 비슷하게 Api단만 만들어 놓고 서비스에 분리 시킬지 논의 필요

	private final AuctionReader auctionReader;
	private final MemberReader memberReader;

	// TODO : BIDREADER로 대체 해야함
	// private final BidRepository bidRepository;
	// private final BidService bidService;

	@Override
	public MypageInfo.MemberInfoResponse findCurrentMemberInfo(Long memberId) {
		log.debug("find me start for member {}", memberId);

		Member member = memberReader.findById(memberId);

		// TODO : 개선 필요(서버측 문제?) , Exception
		log.debug("find me finished for member {}", memberId);

		return MypageInfo.MemberInfoResponse.toDto(member);
	}

	// @Override
	// @Transactional(readOnly = true)
	// public Page<MypageInfo.MypageAuctionListResponse> findMyAuctionList(
	// 	Long memberId,
	// 	AuctionStatus auctionStatus,
	// 	Pageable pageable
	// ) {
	//
	// 	log.debug("find transaction auction list start for member {}", memberId);
	//
	// 	Member memberEntity = memberReader.findById(memberId);
	//
	// 	// TODO : 이미지 갯수 논의 필요
	// 	Page<Auction> myAuctionList = auctionReader.searchMyAuctionList(memberEntity.getMemberId(), auctionStatus,
	// 		pageable);
	//
	// 	// DTO로 변환
	// 	Page<MypageInfo.MypageAuctionListResponse> content = myAuctionList.map(auction -> {
	// 		List<String> imageKeys = auction.getImages().stream()
	// 			.map(Image::getImagePath)
	// 			.collect(Collectors.toList());
	//
	// 		return new MypageInfo.MypageAuctionListResponse(
	// 			auction.getAuctionId(),
	// 			auction.getTitle(),
	// 			auction.getAuctionStatus(),
	// 			bidService.getCurrentHighestBidPrice(auction.getAuctionId()),
	// 			auction.getEndDate(),
	// 			imageKeys
	// 		);
	// 	});
	//
	// 	log.debug("get transaction auction list finish for member {}", memberId);
	//
	// 	return content;
	// }

	// TODO : bid 기능 추가 후 수정
	// @Override
	// @Transactional(readOnly = true)
	// public Page<MypageBidListResponse> findMyBidList(Long memberId, Pageable pageable) {
	//
	// 	log.debug("get transaction bid list start for member {}", memberId);
	//
	// 	Member member = memberReader.findById(memberId);
	//
	// 	// TODO : 이미지 갯수 논의 필요
	// 	Page<BidEntity> bidPageList = bidRepository.searchMyBidList(memberEntity.getMemberId(), pageable);
	//
	// 	// DTO로 변환
	// 	Page<MypageBidListResponse> content = bidPageList.map(auction -> {
	// 		List<String> imageKeys = auction.getAuctionEntity().getImages().stream()
	// 			.map(ImageEntity::getS3Key)
	// 			.collect(Collectors.toList());
	// 		return new MypageBidListResponse(
	// 			auction.getAuctionEntity().getAuctionId(),
	// 			auction.getAuctionEntity().getTitle(),
	// 			auction.getBidPrice(),
	// 			auction.getAuctionEntity().getEndDate(),
	// 			imageKeys
	// 		);
	//
	// 	});
	//
	// 	log.debug("get transaction bid finish list for member {}", memberId);
	//
	// 	return content;
	//
	// }
}
