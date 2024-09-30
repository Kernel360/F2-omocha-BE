package org.auction.client.auction.interfaces;

import static org.auction.client.common.code.AuctionCode.*;

import java.util.List;

import org.auction.client.auction.application.AuctionService;
import org.auction.client.auction.interfaces.request.CreateAuctionRequest;
import org.auction.client.auction.interfaces.response.AuctionDetailResponse;
import org.auction.client.auction.interfaces.response.CreateAuctionResponse;
import org.auction.client.common.dto.ResultDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

	// REFACTOR : 로그인 구현이 완료되면 PathVariable에 있는 User Id를 삭제할 예정
	@Override
	@PostMapping(value = "/{member_id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResultDto<CreateAuctionResponse>> auctionSave(
		@PathVariable("member_id") Long memberId,
		@RequestPart("auctionRequest") CreateAuctionRequest auctionRequest,
		@RequestPart(value = "images", required = true) List<MultipartFile> images
	) {
		log.info("Received CreateAuctionRequest: {}", auctionRequest);
		log.debug("Create auction post started");

		// TODO : 소셜로그인 구현 시 SecurityContextHolder에서 User 정보를 가져올 수 있다.

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
		@PathVariable("auction_id") Long auctionId
	) {
		log.debug("Remove auction post started");

		auctionService.removeAuction(auctionId);

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