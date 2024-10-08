package org.auction.client.auction.interfaces;

import static org.auction.client.common.code.AuctionCode.*;

import java.util.List;

import org.auction.client.auction.application.AuctionService;
import org.auction.client.auction.interfaces.request.CreateAuctionRequest;
import org.auction.client.auction.interfaces.response.AuctionDetailResponse;
import org.auction.client.auction.interfaces.response.AuctionListResponse;
import org.auction.client.auction.interfaces.response.CreateAuctionResponse;
import org.auction.client.common.dto.ResultDto;
import org.auction.client.jwt.UserPrincipal;
import org.auction.domain.auction.infrastructure.condition.AuctionSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auction")
public class AuctionController implements AuctionApi {

	private final AuctionService auctionService;

	@Override
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResultDto<CreateAuctionResponse>> auctionSave(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@RequestPart("auctionRequest") CreateAuctionRequest auctionRequest,
		@RequestPart(value = "images", required = true) List<MultipartFile> images
	) {
		log.info("Received CreateAuctionRequest: {}", auctionRequest);
		log.debug("Create auction post started");

		Long memberId = userPrincipal.getId();
		log.info("memberId = {}", memberId);

		CreateAuctionResponse response = auctionService.addAuction(auctionRequest, images, memberId);

		ResultDto<CreateAuctionResponse> resultDto = ResultDto.res(
			AUCTION_CREATE_SUCCESS.getStatusCode(),
			AUCTION_CREATE_SUCCESS.getResultMsg(),
			response
		);

		return ResponseEntity
			.status(AUCTION_CREATE_SUCCESS.getHttpStatus())
			.body(resultDto);
	}

	@Override
	@GetMapping("/basic-list")
	public ResponseEntity<ResultDto<Page<AuctionListResponse>>> auctionList(
		AuctionSearchCondition condition,
		@PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC)
		Pageable pageable
	) {
		Page<AuctionListResponse> response = auctionService.searchAuction(condition, pageable);

		ResultDto<Page<AuctionListResponse>> resultDto = ResultDto.res(
			AUCTION_LIST_ACCESS_SUCCESS.getStatusCode(),
			AUCTION_LIST_ACCESS_SUCCESS.getResultMsg(),
			response
		);

		return ResponseEntity
			.status(AUCTION_LIST_ACCESS_SUCCESS.getHttpStatus())
			.body(resultDto);
	}

	@Override
	@GetMapping("/{auction_id}")
	public ResponseEntity<ResultDto<AuctionDetailResponse>> auctionDetails(
		@PathVariable("auction_id") Long auctionId
	) {
		log.info("Received GetAuctionDetailRequest: {}", auctionId);
		log.debug("Get auction post started");

		AuctionDetailResponse response = auctionService.findAuctionDetails(auctionId);

		ResultDto<AuctionDetailResponse> resultDto = ResultDto.res(
			AUCTION_DETAIL_SUCCESS.getStatusCode(),
			AUCTION_DETAIL_SUCCESS.getResultMsg(),
			response
		);

		return ResponseEntity
			.status(AUCTION_DETAIL_SUCCESS.getHttpStatus())
			.body(resultDto);
	}

	@Override
	@DeleteMapping("/{auction_id}")
	public ResponseEntity<ResultDto<Void>> auctionRemove(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@PathVariable("auction_id") Long auctionId
	) {
		log.debug("Remove auction post started");

		auctionService.removeAuction(userPrincipal.getId(), auctionId);

		ResultDto<Void> resultDto = ResultDto.res(
			AUCTION_DELETE_SUCCESS.getStatusCode(),
			AUCTION_DELETE_SUCCESS.getResultMsg(),
			null
		);

		return ResponseEntity
			.status(AUCTION_DELETE_SUCCESS.getHttpStatus())
			.body(resultDto);
	}

}