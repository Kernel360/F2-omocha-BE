package org.omocha.domain.member.mypage;

public interface MypageService {

	MypageInfo.MemberInfoResponse findCurrentMemberInfo(Long memberId);

	// Page<AuctionInfo.MypageAuctionListResponse> findMyAuctionList(
	// 	Long memberId,
	// 	AuctionStatus auctionStatus,
	// 	Pageable pageable
	// );

	// Page<MypageBidListResponse> findMyBidList(Long memberId, Pageable pageable);
}
