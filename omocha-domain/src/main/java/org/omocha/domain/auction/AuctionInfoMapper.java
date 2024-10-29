package org.omocha.domain.auction;

import java.util.List;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
	componentModel = "spring",
	injectionStrategy = InjectionStrategy.CONSTRUCTOR,
	unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface AuctionInfoMapper {

	AuctionInfo.AuctionDetailResponse toResponse(Auction auction, List<String> imagePaths);
}
