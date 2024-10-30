package org.omocha.api.application;

import org.omocha.domain.member.mypage.MypageInfo;
import org.omocha.domain.member.mypage.MypageService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MypageFacade {

	private final MypageService mypageService;

	public MypageInfo.MemberInfoResponse findCurrentMemberInfo(Long memberId) {

		MypageInfo.MemberInfoResponse memberInfoResponse = mypageService.findCurrentMemberInfo(memberId);

		return memberInfoResponse;

	}

	// public Page<MypageInfo.MypageAuctionListResponse> findMyAuctionList(Long memberId, AuctionStatus auctionStatus,
	// 	Pageable pageable) {
	//
	// 	Page<MypageInfo.MypageAuctionListResponse> auctionListResponses = mypageService
	// 		.findMyAuctionList(memberId, auctionStatus, pageable);
	//
	// 	return auctionListResponses;
	// }

}
