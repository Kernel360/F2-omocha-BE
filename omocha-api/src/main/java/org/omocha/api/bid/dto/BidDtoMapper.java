package org.omocha.api.bid.dto;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.omocha.api.common.util.ValueObjectMapper;
import org.omocha.domain.bid.BidCommand;
import org.omocha.domain.bid.BidInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

@Mapper(
	componentModel = "spring",
	injectionStrategy = InjectionStrategy.CONSTRUCTOR,
	unmappedTargetPolicy = ReportingPolicy.ERROR,
	uses = ValueObjectMapper.class
)
public interface BidDtoMapper {

	// 통합된 메소드
	default <T, R> Page<R> toResponsePage(Page<T> pageInfo, Function<T, R> mapper) {
		List<R> content = pageInfo.getContent().stream()
			.map(mapper)
			.collect(Collectors.toList());

		return new PageImpl<>(content, pageInfo.getPageable(), pageInfo.getTotalElements());
	}

	List<BidDto.BidListResponse> toResponse(List<BidInfo.BidList> bidList);

	@Mapping(target = "bidPrice", source = "createRequest.bidPrice", qualifiedByName = "toPrice")
	BidCommand.AddBid toCommand(Long buyerMemberId, Long auctionId, BidDto.BidAddRequest createRequest);

	BidDto.BidAddResponse toResponse(BidInfo.AddBid createResponse);

	BidDto.NowPriceResponse toResponse(BidInfo.NowPrice nowPrice);

	BidCommand.RetrieveMyBids toMyBidsCommand(Long memberId, Long auctionId);

	// BidInfo.RetrieveMyBids에 대한 변환
	default Page<BidDto.MyBidListResponse> toMyBidListResponse(Page<BidInfo.RetrieveMyBids> retrieveMyBidsInfo) {
		return toResponsePage(retrieveMyBidsInfo, this::toResponse);
	}

	BidDto.MyBidListResponse toResponse(BidInfo.RetrieveMyBids retrieveMyBidsInfo);

	BidCommand.BuyNow toCommand(Long buyerMemberId, Long auctionId);
}
