package org.omocha.api.application;

import org.omocha.api.common.util.PasswordManager;
import org.omocha.domain.auction.AuctionCommand;
import org.omocha.domain.auction.AuctionInfo;
import org.omocha.domain.auction.AuctionService;
import org.omocha.domain.auction.bid.BidCommand;
import org.omocha.domain.auction.bid.BidInfo;
import org.omocha.domain.auction.bid.BidService;
import org.omocha.domain.member.MemberCommand;
import org.omocha.domain.member.MemberInfo;
import org.omocha.domain.member.MemberService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MypageFacade {

	private final MemberService memberService;
	private final AuctionService auctionService;
	private final BidService bidService;
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

		passwordManager.match(modifyPasswordCommand.currentPassword(), retrievePasswordInfo.password());

		memberService.modifyPassword(modifyPasswordCommand);

	}

	public MemberInfo.modifyProfileImage modifyProfileImage(
		MemberCommand.ModifyProfileImage modifyProfileImageCommand) {

		return memberService.modifyProfileImage(modifyProfileImageCommand);
	}

	public Page<AuctionInfo.RetrieveMyAuctions> retrieveMyAuctions(
		AuctionCommand.RetrieveMyAuctions retrieveMyAuctionsCommand, Pageable pageable) {

		Page<AuctionInfo.RetrieveMyAuctions> retrieveMyAuctionsInfo = auctionService
			.retrieveMyAuctions(retrieveMyAuctionsCommand, pageable);

		return retrieveMyAuctionsInfo;
	}

	public Page<BidInfo.RetrieveMyBids> retrieveMyBids(BidCommand.RetrieveMyBids retrieveMyBidsCommand,
		Pageable sortPage) {
		Page<BidInfo.RetrieveMyBids> retrieveMyBids = bidService.retrieveMyBids(retrieveMyBidsCommand, sortPage);

		return retrieveMyBids;
	}
}
