package org.omocha.api.interfaces.mapper;

import java.util.List;
import java.util.function.Function;
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

@Mapper(componentModel = "spring",
	injectionStrategy = InjectionStrategy.CONSTRUCTOR,
	unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface MypageDtoMapper {

	// 통합된 메소드
	default <T, R> Page<R> toResponsePage(Page<T> pageInfo, Function<T, R> mapper) {
		List<R> content = pageInfo.getContent().stream()
			.map(mapper)
			.collect(Collectors.toList());

		return new PageImpl<>(content, pageInfo.getPageable(), pageInfo.getTotalElements());
	}

	MypageDto.CurrentMemberInfoResponse toResponse(MemberInfo.RetrieveCurrentMemberInfo retrieveCurrentMemberInfo);

	MemberCommand.ModifyBasicInfo toCommand(Long memberId, MypageDto.MemberModifyRequest memberModifyRequest);

	MypageDto.MemberModifyResponse toResponse(MemberInfo.ModifyBasicInfo modifyBasicInfoInfo);

	MemberCommand.ModifyPassword toCommand(Long memberId, String currentPassword, String newPassword);

	MemberCommand.ModifyProfileImage toCommand(Long memberId, MultipartFile profileImage);

	MypageDto.ProfileImageModifyResponse toResponse(MemberInfo.modifyProfileImage modifyProfileImage);

	// retrieveMyAuctions
	AuctionCommand.RetrieveMyAuctions toCommand(Long memberId, Auction.AuctionStatus auctionStatus);

	// AuctionInfo.RetrieveMyAuctions에 대한 변환
	default Page<MypageDto.MyAuctionListResponse> toMyAuctionListResponse(
		Page<AuctionInfo.RetrieveMyAuctions> retrieveMyAuctionsInfo) {
		return toResponsePage(retrieveMyAuctionsInfo, this::toResponse);
	}

	MypageDto.MyAuctionListResponse toResponse(AuctionInfo.RetrieveMyAuctions retrieveMyAuctions);

	// BidInfo.RetrieveMyBids에 대한 변환
	default Page<MypageDto.MyBidListResponse> toMyBidListResponse(Page<BidInfo.RetrieveMyBids> retrieveMyBidsInfo) {
		return toResponsePage(retrieveMyBidsInfo, this::toResponse);
	}

	MypageDto.MyBidListResponse toResponse(BidInfo.RetrieveMyBids retrieveMyBidsInfo);

	// retrieveMyBids
	BidCommand.RetrieveMyBids toCommand(Long memberId, Long auctionId);

	BidCommand.RetrieveMyBidAuctions toBidAuctionCommand(Long memberId);

	default Page<MypageDto.MyBidAuctionResponse> toMyBiductionListResponse(
		Page<BidInfo.RetrieveMyBidAuctions> retrieveMyBidAuctionsInfo) {
		return toResponsePage(retrieveMyBidAuctionsInfo, this::toResponse);
	}

	MypageDto.MyBidAuctionResponse toResponse(BidInfo.RetrieveMyBidAuctions retrieveMyBidAuctionsInfo);

}
