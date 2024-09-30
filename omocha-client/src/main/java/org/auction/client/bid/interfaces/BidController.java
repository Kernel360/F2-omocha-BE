package org.auction.client.bid.interfaces;

import static org.auction.client.common.code.BidCode.*;

import java.util.List;

import org.auction.client.bid.application.BidService;
import org.auction.client.bid.interfaces.request.CreateBidRequest;
import org.auction.client.bid.interfaces.response.BidResponse;
import org.auction.client.bid.interfaces.response.CreateBidResponse;
import org.auction.client.common.dto.ResultDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bid")
public class BidController implements BidApi {

	private final BidService bidService;

	@GetMapping("/{auction_id}")
	public ResponseEntity<ResultDto<List<BidResponse>>> bidList(
		@PathVariable("auction_id") Long auctionId

	) {

		List<BidResponse> bidList = bidService.findBidList(auctionId);

		ResultDto<List<BidResponse>> resultDto = ResultDto.res(
			BIDDING_GETLIST_SUCCESS.getStatusCode(),
			BIDDING_GETLIST_SUCCESS.getMessage(),
			bidList
		);

		return ResponseEntity
			.status(BIDDING_GETLIST_SUCCESS.getHttpStatus())
			.body(resultDto);

	}

	@PostMapping("/{auction_id}")
	public ResponseEntity<ResultDto<CreateBidResponse>> bidAdd(
		@PathVariable("auction_id") Long auctionId,
		@RequestParam("buyer_id") Long buyerId,
		@RequestBody CreateBidRequest createBidRequest
	) {

		CreateBidResponse createBidResponse = bidService.addBid(auctionId, buyerId, createBidRequest);

		ResultDto<CreateBidResponse> resultDto = ResultDto.res(
			BIDDING_ADD_SUCCESS.getStatusCode(),
			BIDDING_ADD_SUCCESS.getMessage(),
			createBidResponse
		);

		return ResponseEntity
			.status(BIDDING_ADD_SUCCESS.getHttpStatus())
			.body(resultDto);

	}

}
