package org.omocha.api.interfaces;

import static org.omocha.domain.exception.code.SuccessCode.*;

import java.util.List;

import org.omocha.api.application.AuctionFacade;
import org.omocha.api.common.auth.jwt.UserPrincipal;
import org.omocha.api.common.response.ResultDto;
import org.omocha.api.interfaces.dto.AuctionDto;
import org.omocha.api.interfaces.mapper.AuctionDtoMapper;
import org.omocha.domain.auction.Auction;
import org.omocha.domain.auction.AuctionCommand;
import org.omocha.domain.auction.AuctionInfo;
import org.omocha.domain.common.util.PageSort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/auction")
public class AuctionController implements AuctionApi {

	private final AuctionFacade auctionFacade;
	private final AuctionDtoMapper auctionDtoMapper;
	private final PageSort pageSort;

	@PostMapping(
		consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<ResultDto<AuctionDto.AuctionAddResponse>> auctionAdd(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@RequestPart("auctionRequest") AuctionDto.AuctionAddRequest auctionRequest,
		@RequestPart(value = "images", required = true) List<MultipartFile> images,
		@RequestPart(value = "thumbnailPath", required = true) MultipartFile thumbnailPath
	) {
		log.info("Received auction add request: {}", auctionRequest);

		Long memberId = userPrincipal.getId();

		AuctionCommand.AddAuction auctionCommand = auctionDtoMapper.toCommand(
			auctionRequest, memberId, images, thumbnailPath);

		Long auctionId = auctionFacade.addAuction(auctionCommand);

		AuctionDto.AuctionAddResponse response = auctionDtoMapper.toResponse(auctionId);

		ResultDto<AuctionDto.AuctionAddResponse> result = ResultDto.res(
			AUCTION_CREATE_SUCCESS.getStatusCode(),
			AUCTION_CREATE_SUCCESS.getDescription(),
			response
		);

		log.info("Auction created by memberId : {}", memberId);

		return ResponseEntity
			.status(AUCTION_CREATE_SUCCESS.getHttpStatus())
			.body(result);
	}

	@GetMapping("/basic-list")
	public ResponseEntity<ResultDto<Page<AuctionDto.AuctionSearchResponse>>> auctionSearchList(
		AuctionDto.AuctionSearchRequest searchRequest,
		@RequestParam(value = "categoryId", required = false) Long categoryId,
		@RequestParam(value = "auctionStatus", required = false) Auction.AuctionStatus auctionStatus,
		@RequestParam(value = "sort", defaultValue = "createdAt") String sort,
		@RequestParam(value = "direction", defaultValue = "DESC") String direction,
		@PageableDefault(page = 0, size = 10)
		Pageable pageable
	) {

		Pageable sortPage = pageSort.sortPage(pageable, sort, direction);

		AuctionCommand.SearchAuction searchCommand =
			auctionDtoMapper.toCommand(searchRequest, auctionStatus, categoryId);

		Page<AuctionInfo.SearchAuction> searchInfo = auctionFacade.searchAuction(searchCommand, sortPage);

		Page<AuctionDto.AuctionSearchResponse> response = auctionDtoMapper.toAuctionListResponse(searchInfo);

		ResultDto<Page<AuctionDto.AuctionSearchResponse>> result = ResultDto.res(
			AUCTION_LIST_ACCESS_SUCCESS.getStatusCode(),
			AUCTION_LIST_ACCESS_SUCCESS.getDescription(),
			response
		);

		return ResponseEntity
			.status(AUCTION_LIST_ACCESS_SUCCESS.getHttpStatus())
			.body(result);
	}

	@GetMapping("/{auction_id}")
	public ResponseEntity<ResultDto<AuctionDto.AuctionDetailsResponse>> auctionDetails(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@PathVariable("auction_id") Long auctionId
	) {
		log.info("Received auction details request: {}", auctionId);

		AuctionCommand.RetrieveAuction auctionCommand = auctionDtoMapper.toCommand(auctionId, userPrincipal.getId());
		AuctionInfo.RetrieveAuction detailInfo = auctionFacade.retrieveAuction(auctionCommand);
		AuctionDto.AuctionDetailsResponse response = auctionDtoMapper.toResponse(detailInfo);

		log.info("Auction details retrieved auctionId : {}", auctionId);

		ResultDto<AuctionDto.AuctionDetailsResponse> result = ResultDto.res(
			AUCTION_DETAIL_SUCCESS.getStatusCode(),
			AUCTION_DETAIL_SUCCESS.getDescription(),
			response
		);

		return ResponseEntity
			.status(AUCTION_DETAIL_SUCCESS.getHttpStatus())
			.body(result);

	}

	@Override
	@DeleteMapping("/{auction_id}")
	public ResponseEntity<ResultDto<Void>> auctionRemove(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@PathVariable("auction_id") Long auctionId
	) {
		log.info("Received auction remove request: {}, memberId: {}", auctionId, userPrincipal.getId());

		AuctionCommand.RemoveAuction removeCommand =
			auctionDtoMapper.toRemoveCommand(userPrincipal.getId(), auctionId);

		auctionFacade.removeAuction(removeCommand);

		log.info("Auction removed by memberId: {}, auctionId: {}", userPrincipal.getId(), auctionId);

		ResultDto<Void> result = ResultDto.res(
			AUCTION_DELETE_SUCCESS.getStatusCode(),
			AUCTION_DELETE_SUCCESS.getDescription()
		);

		return ResponseEntity
			.status(AUCTION_DELETE_SUCCESS.getHttpStatus())
			.body(result);
	}

	@Override
	@GetMapping("/my")
	public ResponseEntity<ResultDto<Page<AuctionDto.MyAuctionListResponse>>> myAuctionList(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@RequestParam(value = "auctionStatus", required = false) Auction.AuctionStatus auctionStatus,
		@RequestParam(value = "sort", defaultValue = "createdAt") String sort,
		@RequestParam(value = "direction", defaultValue = "DESC") String direction,
		@PageableDefault(page = 0, size = 10)
		Pageable pageable
	) {

		log.info("myAuctionList started memberId : {} , auctionStatus : {}", userPrincipal.getId(), auctionStatus);

		Long memberId = userPrincipal.getId();

		Pageable sortPage = pageSort.sortPage(pageable, sort, direction);

		AuctionCommand.RetrieveMyAuctions retrieveMyAuctionsCommand = auctionDtoMapper.toCommand(
			memberId,
			auctionStatus
		);

		Page<AuctionInfo.RetrieveMyAuctions> retrieveMyAuctionsInfo = auctionFacade.retrieveMyAuctions(
			retrieveMyAuctionsCommand,
			sortPage
		);

		Page<AuctionDto.MyAuctionListResponse> myAuctionListResponse = auctionDtoMapper.toMyAuctionListResponse(
			retrieveMyAuctionsInfo
		);

		ResultDto<Page<AuctionDto.MyAuctionListResponse>> resultDto = ResultDto.res(
			MY_AUCTION_LIST_SUCCESS.getStatusCode(),
			MY_AUCTION_LIST_SUCCESS.getDescription(),
			myAuctionListResponse
		);

		return ResponseEntity
			.status(MY_AUCTION_LIST_SUCCESS.getHttpStatus())
			.body(resultDto);

	}

	@GetMapping("/my-bid")
	public ResponseEntity<ResultDto<Page<AuctionDto.MyBidAuctionResponse>>> myBidAuctionList(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@RequestParam(value = "sort", defaultValue = "createdAt") String sort,
		@RequestParam(value = "direction", defaultValue = "DESC") String direction,
		@PageableDefault(page = 0, size = 10)
		Pageable pageable
	) {

		log.info("myBidAuctionList started memberId : {} ", userPrincipal.getId());

		Long memberId = userPrincipal.getId();

		Pageable sortPage = pageSort.sortPage(pageable, sort, direction);

		AuctionCommand.RetrieveMyBidAuctions retrieveMyBidAuctionsCommand = auctionDtoMapper.toBidAuctionCommand(
			memberId);

		Page<AuctionInfo.RetrieveMyBidAuctions> retrieveMyBidAuctionsInfo = auctionFacade.retrieveMyBidAuctions(
			retrieveMyBidAuctionsCommand,
			sortPage
		);

		Page<AuctionDto.MyBidAuctionResponse> myBidAuctionListResponse = auctionDtoMapper.toMyBidAuctionListResponse(
			retrieveMyBidAuctionsInfo);

		ResultDto<Page<AuctionDto.MyBidAuctionResponse>> resultDto = ResultDto.res(
			MY_BIDDING_AUCTION_LIST_SUCCESS.getStatusCode(),
			MY_BIDDING_AUCTION_LIST_SUCCESS.getDescription(),
			myBidAuctionListResponse
		);

		log.info("myBidAuctionList finished");

		return ResponseEntity
			.status(MY_BIDDING_AUCTION_LIST_SUCCESS.getHttpStatus())
			.body(resultDto);

	}
}
