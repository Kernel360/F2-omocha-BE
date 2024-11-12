package org.omocha.api.application;

import org.omocha.api.common.util.PasswordManager;
import org.omocha.domain.auction.AuctionService;
import org.omocha.domain.member.MemberCommand;
import org.omocha.domain.member.MemberInfo;
import org.omocha.domain.member.MemberService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MypageFacade {

	private final MemberService memberService;
	private final AuctionService auctionService;
	private final PasswordManager passwordManager;

	public MemberInfo.RetrieveCurrentMemberInfo retrieveCurrentMemberInfo(Long memberId) {

		return memberService.retrieveCurrentMemberInfo(memberId);

	}

	public MemberInfo.ModifyBasicInfo modifyBasicInfo(MemberCommand.ModifyBasicInfo modifyBasicInfoCommand) {

		return memberService.modifyBasicInfo(modifyBasicInfoCommand);

	}

	public void modifyPassword(MemberCommand.ModifyPassword modifyPasswordCommand) {

		MemberInfo.RetrievePassword retrievePasswordInfo = memberService.retrievePassword(
			modifyPasswordCommand.memberId());

		passwordManager.match(
			modifyPasswordCommand.currentPassword(),
			retrievePasswordInfo.password(),
			modifyPasswordCommand.memberId()
		);

		memberService.modifyPassword(modifyPasswordCommand);

	}

	public MemberInfo.modifyProfileImage modifyProfileImage(
		MemberCommand.ModifyProfileImage modifyProfileImageCommand) {

		return memberService.modifyProfileImage(modifyProfileImageCommand);
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
