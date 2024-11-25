package org.omocha.api.bid;

import static org.omocha.domain.common.code.SuccessCode.*;

import java.util.List;

import org.omocha.api.auth.jwt.UserPrincipal;
import org.omocha.api.bid.dto.BidDto;
import org.omocha.api.bid.dto.BidDtoMapper;
import org.omocha.api.common.response.ResultDto;
import org.omocha.domain.bid.BidCommand;
import org.omocha.domain.bid.BidInfo;
import org.omocha.domain.common.util.PageSort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/bids")
public class BidController implements BidApi {

	private final BidFacade bidFacade;
	private final BidDtoMapper bidDtoMapper;
	private final PageSort pageSort;

	@Override
	@GetMapping("/{auction_id}")
	public ResponseEntity<ResultDto<List<BidDto.BidListResponse>>> bidList(
		@PathVariable("auction_id") Long auctionId
	) {
		List<BidInfo.BidList> bidList = bidFacade.retrieveBids(auctionId);

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

	@Override
	@PostMapping("/{auction_id}")
	public ResponseEntity<ResultDto<BidDto.BidAddResponse>> bidAdd(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@PathVariable("auction_id") Long auctionId,
		@RequestBody @Valid BidDto.BidAddRequest addRequest
	) {
		Long buyerMemberId = userPrincipal.getId();
		BidCommand.AddBid addBidCommand = bidDtoMapper.toCommand(buyerMemberId, auctionId, addRequest);

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

	@Override
	@GetMapping("/{auction_id}/now-price")
	public ResponseEntity<ResultDto<BidDto.NowPriceResponse>> nowPrice(
		@PathVariable("auction_id") Long auctionId
	) {
		BidInfo.NowPrice nowPrice = bidFacade.retrieveNowPrice(auctionId);
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

	@Override
	@GetMapping("/me/{auction_id}")
	public ResponseEntity<ResultDto<Page<BidDto.MyBidListResponse>>> myBidList(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@PathVariable(name = "auction_id") Long auctionId,
		@RequestParam(value = "sort", defaultValue = "createdAt") String sort,
		@RequestParam(value = "direction", defaultValue = "DESC") String direction,
		@PageableDefault(page = 0, size = 10)
		Pageable pageable
	) {

		log.info("myBidList started memberId : {} ", userPrincipal.getId());

		Long memberId = userPrincipal.getId();

		Pageable sortPage = pageSort.sortPage(pageable, sort, direction);

		BidCommand.RetrieveMyBids retrieveMyBidsCommand = bidDtoMapper.toMyBidsCommand(memberId, auctionId);

		Page<BidInfo.RetrieveMyBids> retrieveMyBidsInfo = bidFacade.retrieveMyBids(retrieveMyBidsCommand,
			sortPage);

		Page<BidDto.MyBidListResponse> myBidListResponse = bidDtoMapper.toMyBidListResponse(
			retrieveMyBidsInfo);

		ResultDto<Page<BidDto.MyBidListResponse>> resultDto = ResultDto.res(
			MY_BIDDING_LIST_SUCCESS.getStatusCode(),
			MY_BIDDING_LIST_SUCCESS.getDescription(),
			myBidListResponse
		);

		log.info("myBidList finished");

		return ResponseEntity
			.status(MY_BIDDING_LIST_SUCCESS.getHttpStatus())
			.body(resultDto);

	}

	@Override
	@PostMapping("/{auction_id}/instant-buy")
	public ResponseEntity<ResultDto<Void>> instantBuy(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@PathVariable("auction_id") Long auctionId
	) {
		Long buyerMemberId = userPrincipal.getId();

		BidCommand.BuyNow buyNowCommand = bidDtoMapper.toCommand(buyerMemberId, auctionId);

		bidFacade.buyNow(buyNowCommand);

		ResultDto<Void> result = ResultDto.res(
			AUCTION_INSTANT_BUY_SUCCESS.getStatusCode(),
			AUCTION_INSTANT_BUY_SUCCESS.getDescription()
		);

		return ResponseEntity
			.status(AUCTION_INSTANT_BUY_SUCCESS.getHttpStatus())
			.body(result);
	}
}