package org.omocha.api.application;

import org.omocha.domain.auction.AuctionService;
import org.omocha.domain.member.MemberCommand;
import org.omocha.domain.member.MemberInfo;
import org.omocha.domain.member.MemberService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MypageFacade {

	private final MemberService memberService;
	private final AuctionService auctionService;
	private final PasswordEncoder passwordEncoder;

	public MemberInfo.CurrentMemberInfo findCurrentMemberInfo(Long memberId) {

		return memberService.findCurrentMemberInfo(memberId);

	}

	public MemberInfo.MemberModifyInfo modifyBasicInfoMember(MemberCommand.MemberModify memberModifyCommand) {

		return memberService.modifyBasicInfo(memberModifyCommand);

	}

	public void modifyPassword(MemberCommand.PasswordModify passwordModifyCommand) {

		// // TODO : 멘토링 이후 수정 필요

		MemberInfo.MemberDetail memberDetail = memberService.findMember(passwordModifyCommand.memberId());

		MemberInfo.Login loginInfo = memberService.findMember(memberDetail.email());

		if (!passwordEncoder.matches(passwordModifyCommand.currentPassword(), loginInfo.password())) {
			throw new RuntimeException("Current password is incorrect");
		}

		passwordModifyCommand = new MemberCommand.PasswordModify(
			passwordModifyCommand.memberId(),
			passwordEncoder.encode(passwordModifyCommand.currentPassword()),
			passwordEncoder.encode(passwordModifyCommand.newPassword())

		);

		memberService.modifyPassword(passwordModifyCommand);

	}

	public MemberInfo.ProfileImageInfo modifyProfileImage(
		MemberCommand.ProfileImageModify modifyProfileImageCommand) {

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
