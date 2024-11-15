package org.omocha.api.interfaces.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.omocha.api.interfaces.dto.AuctionDto;
import org.omocha.domain.auction.Auction;
import org.omocha.domain.auction.AuctionCommand;
import org.omocha.domain.auction.AuctionInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.multipart.MultipartFile;

@Mapper(
	componentModel = "spring",
	injectionStrategy = InjectionStrategy.CONSTRUCTOR,
	unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface AuctionDtoMapper {

	AuctionCommand.AddAuction toCommand(AuctionDto.AuctionAddRequest auctionRequest, Long memberId,
		List<MultipartFile> images, MultipartFile thumbnailPath);

	AuctionCommand.SearchAuction toCommand(
		AuctionDto.AuctionSearchRequest condition,
		Auction.AuctionStatus auctionStatus,
		Long categoryId,
		Long memberId
	);

	AuctionCommand.RetrieveAuction toCommand(Long auctionId, Long memberId);

	AuctionDto.AuctionAddResponse toResponse(Long auctionId);

	default Page<AuctionDto.AuctionSearchResponse> toSearchResponse(Page<AuctionInfo.SearchAuction> auctionListResult) {
		List<AuctionDto.AuctionSearchResponse> content = auctionListResult.getContent().stream()
			.map(this::toResponse)
			.collect(Collectors.toList());

		return new PageImpl<>(content, auctionListResult.getPageable(), auctionListResult.getTotalElements());
	}

	AuctionDto.AuctionSearchResponse toResponse(AuctionInfo.SearchAuction auctionInfo);

	AuctionDto.AuctionDetailsResponse toResponse(AuctionInfo.RetrieveAuction auctionDetailResponse);

	AuctionCommand.RemoveAuction toRemoveCommand(Long auctionId, Long memberId);

	AuctionCommand.LikeAuction toLikeCommand(Long auctionId, Long memberId);

	AuctionDto.AuctionLikeResponse toLikeResponse(AuctionInfo.LikeAuction likeResponse);

	default Page<AuctionDto.AuctionLikeListResponse> toLikeListResponse(
		Page<AuctionInfo.RetrieveMyAuctionLikes> myAuctionLikes) {
		List<AuctionDto.AuctionLikeListResponse> content = myAuctionLikes.getContent().stream()
			.map(this::toResponse)
			.collect(Collectors.toList());

		return new PageImpl<>(content, myAuctionLikes.getPageable(), myAuctionLikes.getTotalElements());
	}

	AuctionDto.AuctionLikeListResponse toResponse(AuctionInfo.RetrieveMyAuctionLikes myAuctionLikes);
}
