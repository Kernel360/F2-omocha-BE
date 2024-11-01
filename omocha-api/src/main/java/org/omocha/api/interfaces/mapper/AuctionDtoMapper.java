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

	AuctionCommand.RegisterAuction toCommand(AuctionDto.CreateAuctionRequest auctionRequest, Long memberId,
		List<MultipartFile> images, MultipartFile thumbnailPath);

	AuctionCommand.SearchAuction toCommand(
		AuctionDto.AuctionSearchCondition condition,
		Auction.AuctionStatus auctionStatus
	);

	AuctionCommand.RetrieveAuction toCommand(Long auctionId);

	AuctionDto.CreateAuctionResponse toResponse(Long auctionId);

	default Page<AuctionDto.AuctionListResponse> toResponse(Page<AuctionInfo.AuctionListResponse> auctionListResult) {
		List<AuctionDto.AuctionListResponse> content = auctionListResult.getContent().stream()
			.map(this::toResponse)
			.collect(Collectors.toList());

		return new PageImpl<>(content, auctionListResult.getPageable(), auctionListResult.getTotalElements());
	}

	AuctionDto.AuctionListResponse toResponse(AuctionInfo.AuctionListResponse auctionInfo);

	AuctionDto.AuctionDetailResponse toResponse(AuctionInfo.AuctionDetailResponse auctionDetailResponse);

}
