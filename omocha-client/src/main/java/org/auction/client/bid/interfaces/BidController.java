package org.auction.client.bid.interfaces;

import java.util.List;

import org.auction.client.bid.application.BidService;
import org.auction.client.bid.interfaces.request.CreateBidRequest;
import org.auction.client.bid.interfaces.response.BidResponse;
import org.auction.client.bid.interfaces.response.CreateBidResponse;
import org.auction.client.common.dto.ResultDto;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/v1")
public class BidController implements BidApi {

	private final BidService bidService;

	@GetMapping("/auction/{auction_id}/bid")
	public ResponseEntity<ResultDto<List<BidResponse>>> bidList(
		@PathVariable("auction_id") Long auction_id

	) {

		List<BidResponse> bidList = bidService.findBidList(auction_id);

		ResultDto<List<BidResponse>> resultDto = new ResultDto<>(
			HttpStatus.OK, "성공적으로 입찰을 불러왔습니다.", bidList
		);

		return ResponseEntity.ok(resultDto);

	}

	@PostMapping("/auction/{auction_id}/bid")
	public ResponseEntity<ResultDto<CreateBidResponse>> bidAdd(
		@PathVariable("auction_id") Long auctionId,
		@RequestParam("buyer_id") Long buyerId,
		@RequestBody CreateBidRequest createBidRequest
	) {

		CreateBidResponse createBidResponse = bidService.addBid(auctionId, buyerId, createBidRequest);

		ResultDto<CreateBidResponse> resultDto = new ResultDto<>(
			HttpStatus.OK, "성공적으로 입찰 되었습니다.", createBidResponse
		);

		return ResponseEntity.ok(resultDto);

	}

}
