package org.omocha.api.interfaces.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.omocha.api.interfaces.dto.MypageDto;
import org.omocha.domain.auction.Auction;
import org.omocha.domain.auction.AuctionCommand;
import org.omocha.domain.auction.AuctionInfo;
import org.omocha.domain.auction.bid.BidCommand;
import org.omocha.domain.auction.bid.BidInfo;
import org.omocha.domain.member.MemberCommand;
import org.omocha.domain.member.MemberInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.multipart.MultipartFile;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface MypageDtoMapper {

	MemberCommand.ModifyBasicInfo toCommand(Long memberId, MypageDto.MemberModifyRequest memberModifyRequest);

	MemberCommand.ModifyPassword toCommand(Long memberId, String currentPassword, String newPassword);

	MypageDto.CurrentMemberInfoResponse toResponse(MemberInfo.RetrieveCurrentMemberInfo retrieveCurrentMemberInfo);

	MypageDto.MemberModifyResponse toResponse(MemberInfo.ModifyBasicInfo modifyBasicInfoInfo);

	MypageDto.ProfileImageModifyResponse toResponse(MemberInfo.modifyProfileImage modifyProfileImage);

	MemberCommand.ModifyProfileImage toCommand(Long memberId, MultipartFile profileImage);

	AuctionCommand.RetrieveMyAuctions toCommand(Long memberId, Auction.AuctionStatus auctionStatus);

	default Page<MypageDto.MyAuctionListResponse> toMyAuctionListResponse(
		Page<AuctionInfo.RetrieveMyAuctions> retrieveMyAuctionsInfo) {
		List<MypageDto.MyAuctionListResponse> content = retrieveMyAuctionsInfo.getContent().stream()
			.map(this::toResponse)
			.collect(Collectors.toList());

		return new PageImpl<>(content, retrieveMyAuctionsInfo.getPageable(), retrieveMyAuctionsInfo.getTotalElements());
	}

	MypageDto.MyAuctionListResponse toResponse(AuctionInfo.RetrieveMyAuctions retrieveMyAuctions);

	BidCommand.RetrieveMyBids toCommand(Long memberId);

	default Page<MypageDto.MyBidListResponse> toMyBidListResponse(Page<BidInfo.RetrieveMyBids> retrieveMyBidsInfo) {
		List<MypageDto.MyBidListResponse> content = retrieveMyBidsInfo.getContent().stream()
			.map(this::toResponse)
			.collect(Collectors.toList());

		return new PageImpl<>(content, retrieveMyBidsInfo.getPageable(), retrieveMyBidsInfo.getTotalElements());
	}

	MypageDto.MyBidListResponse toResponse(BidInfo.RetrieveMyBids retrieveMyBidsInfo);

}
