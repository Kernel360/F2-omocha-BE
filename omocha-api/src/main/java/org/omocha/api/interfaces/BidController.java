package org.omocha.api.interfaces;

import static org.omocha.domain.exception.code.SuccessCode.*;

import java.util.List;

import org.omocha.api.application.BidFacade;
import org.omocha.api.common.auth.jwt.UserPrincipal;
import org.omocha.api.common.response.ResultDto;
import org.omocha.api.interfaces.dto.BidDto;
import org.omocha.api.interfaces.mapper.BidDtoMapper;
import org.omocha.domain.auction.bid.BidCommand;
import org.omocha.domain.auction.bid.BidInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/bid")
public class BidController implements BidApi {

	private final BidFacade bidFacade;
	private final BidDtoMapper bidDtoMapper;

	@GetMapping("/{auction_id}")
	public ResponseEntity<ResultDto<List<BidDto.BidListResponse>>> bidList(
		@PathVariable("auction_id") Long auctionId
	) {
		List<BidInfo.BidList> bidList = bidFacade.getBidList(auctionId);

		List<BidDto.BidListResponse> response = bidDtoMapper.toResponse(bidList);

		ResultDto<List<BidDto.BidListResponse>> resultDto = ResultDto.res(
			BIDDING_GET_SUCCESS.getStatusCode(),
			BIDDING_GET_SUCCESS.getDescription(),
			response
		);

		return ResponseEntity
			.status(BIDDING_GET_SUCCESS.getHttpStatus())
			.body(resultDto);
	}

	@PostMapping("/{auction_id}")
	public ResponseEntity<ResultDto<BidDto.BidAddResponse>> bidAdd(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@PathVariable("auction_id") Long auctionId,
		@RequestBody BidDto.BidAddRequest addRequest
	) {
		Long buyerId = userPrincipal.getId();
		BidCommand.AddBid addBidCommand = bidDtoMapper.toCommand(buyerId, auctionId, addRequest);

		BidInfo.AddBid addBid = bidFacade.addBid(addBidCommand);
		BidDto.BidAddResponse response = bidDtoMapper.toResponse(addBid);

		ResultDto<BidDto.BidAddResponse> resultDto = ResultDto.res(
			BIDDING_CREATE_SUCCESS.getStatusCode(),
			BIDDING_CREATE_SUCCESS.getDescription(),
			response
		);

		return ResponseEntity
			.status(BIDDING_CREATE_SUCCESS.getHttpStatus())
			.body(resultDto);
	}

	@GetMapping("/{auction_id}/now-price")
	public ResponseEntity<ResultDto<BidDto.NowPriceResponse>> nowPrice(
		@PathVariable("auction_id") Long auctionId
	) {
		BidInfo.NowPrice nowPrice = bidFacade.getNowPrice(auctionId);
		BidDto.NowPriceResponse response = bidDtoMapper.toResponse(nowPrice);

		ResultDto<BidDto.NowPriceResponse> resultDto = ResultDto.res(
			NOW_PRICE_GET_SUCCESS.getStatusCode(),
			NOW_PRICE_GET_SUCCESS.getDescription(),
			response
		);

		return ResponseEntity
			.status(NOW_PRICE_GET_SUCCESS.getHttpStatus())
			.body(resultDto);
	}
}