package org.omocha.api.interfaces.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.omocha.api.interfaces.dto.AuctionDto;
import org.omocha.domain.auction.AuctionCommand;
import org.omocha.domain.auction.AuctionInfo;
import org.omocha.domain.auction.AuctionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.multipart.MultipartFile;

@Mapper(
	componentModel = "spring",
	injectionStrategy = InjectionStrategy.CONSTRUCTOR,
	unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface AuctionDtoMapper {

	AuctionCommand.RegisterAuction of(AuctionDto.CreateAuctionRequest auctionRequest, List<MultipartFile> images);

	AuctionDto.CreateAuctionResponse of(Long auctionId);

	AuctionCommand.SearchAuction of(AuctionDto.AuctionSearchCondition condition, AuctionStatus auctionStatus);

	default Page<AuctionDto.AuctionListResponse> of(Page<AuctionInfo.Main> auctionListResult) {
		List<AuctionDto.AuctionListResponse> content = auctionListResult.getContent().stream()
			.map(this::of)
			.collect(Collectors.toList());

		return new PageImpl<>(content, auctionListResult.getPageable(), auctionListResult.getTotalElements());
	}

	AuctionDto.AuctionListResponse of(AuctionInfo.Main auctionInfo);
}
