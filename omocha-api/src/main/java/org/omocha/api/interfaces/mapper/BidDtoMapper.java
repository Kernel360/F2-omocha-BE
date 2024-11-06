package org.omocha.api.interfaces.mapper;

import java.util.List;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.omocha.api.interfaces.dto.BidDto;
import org.omocha.domain.auction.bid.BidCommand;
import org.omocha.domain.auction.bid.BidInfo;

@Mapper(
	componentModel = "spring",
	injectionStrategy = InjectionStrategy.CONSTRUCTOR,
	unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface BidDtoMapper {
	List<BidDto.BidListResponse> toResponse(List<BidInfo.BidList> bidList);

	BidCommand.AddBid toCommand(Long buyerId, Long auctionId, BidDto.BidAddRequest createRequest);

	BidDto.BidAddResponse toResponse(BidInfo.AddBid createResponse);

	BidDto.NowPriceResponse toResponse(BidInfo.NowPrice nowPrice);
}
