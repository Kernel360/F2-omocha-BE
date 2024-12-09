package org.omocha.api.auction.dto;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.omocha.api.common.util.ValueObjectMapper;
import org.omocha.domain.auction.Auction;
import org.omocha.domain.auction.AuctionCommand;
import org.omocha.domain.auction.AuctionInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.multipart.MultipartFile;

@Mapper(
	componentModel = "spring",
	injectionStrategy = InjectionStrategy.CONSTRUCTOR,
	unmappedTargetPolicy = ReportingPolicy.ERROR,
	uses = ValueObjectMapper.class
)
public interface AuctionDtoMapper {

	// 통합된 메소드
	default <T, R> Page<R> toResponsePage(Page<T> pageInfo, Function<T, R> mapper) {
		List<R> content = pageInfo.getContent().stream()
			.map(mapper)
			.collect(Collectors.toList());

		return new PageImpl<>(content, pageInfo.getPageable(), pageInfo.getTotalElements());
	}

	@Mapping(target = "startPrice", source = "auctionRequest.startPrice", qualifiedByName = "toPrice")
	@Mapping(target = "instantBuyPrice", source = "auctionRequest.instantBuyPrice", qualifiedByName = "toPrice")
	@Mapping(target = "bidUnit", source = "auctionRequest.bidUnit", qualifiedByName = "toPrice")
	AuctionCommand.AddAuction toCommand(
		AuctionDto.AuctionAddRequest auctionRequest,
		Long memberId,
		List<MultipartFile> images
	);

	AuctionCommand.SearchAuction toCommand(
		AuctionDto.AuctionSearchRequest condition,
		Auction.AuctionStatus auctionStatus,
		Long categoryId,
		Long memberId
	);

	AuctionCommand.RetrieveAuction toCommand(Long auctionId);

	AuctionDto.AuctionAddResponse toResponse(AuctionInfo.AddAuction addAuction);

	default Page<AuctionDto.AuctionSearchResponse> toSearchResponse(Page<AuctionInfo.SearchAuction> auctionListResult) {
		return toResponsePage(auctionListResult, this::toResponse);
	}

	AuctionDto.AuctionSearchResponse toResponse(AuctionInfo.SearchAuction auctionInfo);

	AuctionDto.AuctionDetailsResponse toResponse(AuctionInfo.RetrieveAuction auctionDetailResponse);

	AuctionCommand.RemoveAuction toRemoveCommand(Long auctionId, Long memberId);

	AuctionCommand.LikeAuction toLikeCommand(Long auctionId, Long memberId);

	AuctionDto.AuctionLikeResponse toLikeResponse(AuctionInfo.LikeAuction likeResponse);

	default Page<AuctionDto.AuctionLikeListResponse> toLikeListResponse(
		Page<AuctionInfo.RetrieveMyAuctionLikes> myAuctionLikes) {
		return toResponsePage(myAuctionLikes, this::toResponse);
	}

	AuctionDto.AuctionLikeListResponse toResponse(AuctionInfo.RetrieveMyAuctionLikes myAuctionLikes);

	AuctionCommand.RetrieveMyAuctions toMyCommand(Long memberId, Auction.AuctionStatus auctionStatus);

	AuctionCommand.RetrieveMemberAuctions toMemberCommand(Long memberId, Auction.AuctionStatus auctionStatus);

	default Page<AuctionDto.MyAuctionListResponse> toMyAuctionListResponse(
		Page<AuctionInfo.RetrieveMyAuctions> retrieveMyAuctionsInfo) {
		return toResponsePage(retrieveMyAuctionsInfo, this::toResponse);
	}

	AuctionDto.MyAuctionListResponse toResponse(AuctionInfo.RetrieveMyAuctions retrieveMyAuctions);

	default Page<AuctionDto.MemberAuctionListResponse> toMemberAuctionListResponse(
		Page<AuctionInfo.RetrieveMemberAuctions> retrieveMemberAuctions) {
		return toResponsePage(retrieveMemberAuctions, this::toResponse);
	}

	AuctionDto.MemberAuctionListResponse toResponse(AuctionInfo.RetrieveMemberAuctions retrieveMemberAuctions);

	AuctionCommand.RetrieveMyBidAuctions toBidAuctionCommand(Long memberId);

	default Page<AuctionDto.MyBidAuctionResponse> toMyBidAuctionListResponse(
		Page<AuctionInfo.RetrieveMyBidAuctions> retrieveMyBidAuctionsInfo) {
		return toResponsePage(retrieveMyBidAuctionsInfo, this::toResponse);
	}

	AuctionDto.MyBidAuctionResponse toResponse(AuctionInfo.RetrieveMyBidAuctions retrieveMyBidAuctionsInfo);

}
