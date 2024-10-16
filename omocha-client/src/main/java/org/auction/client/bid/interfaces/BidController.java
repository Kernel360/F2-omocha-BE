package org.auction.client.bid.interfaces;

import static org.auction.client.common.code.BidCode.*;

import java.util.List;

import org.auction.client.auction.application.AuctionService;
import org.auction.client.bid.application.BidService;
import org.auction.client.bid.interfaces.request.CreateBidRequest;
import org.auction.client.bid.interfaces.response.BidResponse;
import org.auction.client.bid.interfaces.response.CreateBidResponse;
import org.auction.client.bid.interfaces.response.NowPriceResponse;
import org.auction.client.common.dto.ResultDto;
import org.auction.client.jwt.UserPrincipal;
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
@RequestMapping("/api/v1/bid")
public class BidController implements BidApi {

	private final BidService bidService;
	private final AuctionService auctionService;

	@GetMapping("/{auction_id}")
	@Override
	public ResponseEntity<ResultDto<List<BidResponse>>> bidList(
		@PathVariable("auction_id") Long auctionId
	) {
		log.debug("Get bidList started");
		log.info("Received GetBidListRequest : {}", auctionId);

		List<BidResponse> bidList = bidService.findBidList(auctionId);

		ResultDto<List<BidResponse>> resultDto = ResultDto.res(
			BIDDING_GET_SUCCESS.getStatusCode(),
			BIDDING_GET_SUCCESS.getResultMsg(),
			bidList
		);

		return ResponseEntity
			.status(BIDDING_GET_SUCCESS.getHttpStatus())
			.body(resultDto);
	}

	@PostMapping("/{auction_id}")
	@Override
	public ResponseEntity<ResultDto<CreateBidResponse>> bidAdd(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@PathVariable("auction_id") Long auctionId,
		@RequestBody CreateBidRequest createBidRequest
	) {
		log.debug("Add bidding started");
		log.info("Received BidAddRequest auctionId : {}", auctionId);
		log.info("Received BidAddRequest createBidRequest : {}", createBidRequest);

		Long buyerId = userPrincipal.getId();

		CreateBidResponse createBidResponse = bidService.addBid(auctionId, buyerId, createBidRequest);

		ResultDto<CreateBidResponse> resultDto = ResultDto.res(
			BIDDING_CREATE_SUCCESS.getStatusCode(),
			BIDDING_CREATE_SUCCESS.getResultMsg(),
			createBidResponse
		);

		return ResponseEntity
			.status(BIDDING_CREATE_SUCCESS.getHttpStatus())
			.body(resultDto);
	}

	@GetMapping("/{auction_id}/now-price")
	@Override
	public ResponseEntity<ResultDto<NowPriceResponse>> nowPrice(
		@PathVariable("auction_id") Long auctionId
	) {
		log.debug("Get nowPrice started");
		log.info("Received nowPrice auctionId : {}", auctionId);

		NowPriceResponse nowPriceResponse = bidService.findNowPrice(auctionId);

		ResultDto<NowPriceResponse> resultDto = ResultDto.res(
			NOW_PRICE_GET_SUCCESS.getStatusCode(),
			NOW_PRICE_GET_SUCCESS.getResultMsg(),
			nowPriceResponse
		);

		return ResponseEntity
			.status(NOW_PRICE_GET_SUCCESS.getHttpStatus())
			.body(resultDto);
	}
}
