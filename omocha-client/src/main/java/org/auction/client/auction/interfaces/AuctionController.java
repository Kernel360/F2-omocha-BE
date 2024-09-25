package org.auction.client.auction.interfaces;

import java.util.List;

import org.auction.client.auction.application.AuctionService;
import org.auction.client.auction.interfaces.request.CreateAuctionRequest;
import org.auction.client.auction.interfaces.response.AuctionDetailResponse;
import org.auction.client.auction.interfaces.response.CreateAuctionResponse;
import org.auction.client.common.dto.ResultDto;
import org.auction.client.image.application.AwsS3Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AuctionController implements AuctionApi {

	private final AuctionService auctionService;
	private final AwsS3Service awsS3Service;

	// REFACTOR : 로그인 구현이 완료되면 PathVariable에 있는 User Id를 삭제할 예정
	@Override
	@PostMapping("/auction/{member_id}")
	public ResponseEntity<ResultDto<CreateAuctionResponse>> auctionSave(
		@PathVariable("member_id") Long memberId,
		@Valid @RequestPart("auctionRequest") CreateAuctionRequest auctionRequest,
		@RequestPart(value = "images", required = false) List<MultipartFile> images
	) {
		log.info("Received CreateAuctionRequest: {} ", auctionRequest);
		log.debug("Create auction post started");
		// TODO : 소셜로그인 구현 시 SecurityContextHolder에서 User 정보를 가져올 수 있다.
		// SecurityContextHolder.getContect().getAuthentication().getName()
		CreateAuctionResponse response = auctionService.addAuction(auctionRequest, images, memberId);
		// TODO : response 할 때 오류 코드와 메시지를 담는 ENUM 클래스 생성하기
		ResultDto<CreateAuctionResponse> resultDto = ResultDto.res(
			HttpStatus.OK, "경매가 성공적으로 생성되었습니다.", response);
		return ResponseEntity.ok(resultDto);
	}

	@Override
	@GetMapping("/auction/{auction_id}")
	public ResponseEntity<ResultDto<AuctionDetailResponse>> auctionDetails(
		@PathVariable("auction_id") Long auctionId
	) {
		log.info("Received GetAuctionDetailRequest: {}", auctionId);
		log.debug("Get auction post started");
		AuctionDetailResponse response = auctionService.findAuctionDetails(auctionId);
		ResultDto<AuctionDetailResponse> resultDto = ResultDto.res(
			HttpStatus.OK, "경매 상세 정보 조회 성공", response);
		return ResponseEntity.ok(resultDto);
	}

	@Override
	@DeleteMapping("/auction/{auction_id}")
	public ResponseEntity<ResultDto<Void>> auctionRemove(
		@PathVariable("auction_id") Long auctionId
	) {
		log.debug("Remove auction post started");
		auctionService.removeAuction(auctionId);
		ResultDto<Void> resultDto = ResultDto.res(
			HttpStatus.OK, "경매가 성공적으로 삭제되었습니다");
		return ResponseEntity.ok(resultDto);
	}
}
