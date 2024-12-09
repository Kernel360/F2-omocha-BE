package org.omocha.api.auction;

import static org.omocha.domain.common.code.SuccessCode.*;

import java.util.List;
import java.util.Optional;

import org.omocha.api.auction.dto.AuctionDto;
import org.omocha.api.auction.dto.AuctionDtoMapper;
import org.omocha.api.auth.jwt.UserPrincipal;
import org.omocha.api.common.response.ResultDto;
import org.omocha.domain.auction.Auction;
import org.omocha.domain.auction.AuctionCommand;
import org.omocha.domain.auction.AuctionInfo;
import org.omocha.domain.common.util.PageSort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/auctions")
public class AuctionController implements AuctionApi {

	private final AuctionFacade auctionFacade;
	private final AuctionDtoMapper auctionDtoMapper;
	private final PageSort pageSort;

	@Override
	@PostMapping(
		consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<ResultDto<AuctionDto.AuctionAddResponse>> auctionAdd(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@RequestPart("auctionRequest") @Valid AuctionDto.AuctionAddRequest auctionRequest,
		@RequestPart(value = "images") List<MultipartFile> images
	) {
		log.info("Received auction add request: {}", auctionRequest);

		Long memberId = userPrincipal.getId();

		AuctionCommand.AddAuction auctionCommand = auctionDtoMapper.toCommand(
			auctionRequest, memberId, images);

		AuctionInfo.AddAuction addInfo = auctionFacade.addAuction(auctionCommand);

		AuctionDto.AuctionAddResponse response = auctionDtoMapper.toResponse(addInfo);

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

	@Override
	@GetMapping("")
	public ResponseEntity<ResultDto<Page<AuctionDto.AuctionSearchResponse>>> auctionSearchList(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		AuctionDto.AuctionSearchRequest searchRequest,
		@RequestParam(value = "categoryId", required = false) Long categoryId,
		@RequestParam(value = "auctionStatus", required = false) Auction.AuctionStatus auctionStatus,
		@RequestParam(value = "sort", defaultValue = "createdAt") String sort,
		@RequestParam(value = "direction", defaultValue = "DESC") String direction,
		@PageableDefault(page = 0, size = 10)
		Pageable pageable
	) {
		Long memberId = Optional.ofNullable(userPrincipal)
			.map(UserPrincipal::getId)
			.orElse(null);

		Pageable sortPage = pageSort.sortPage(pageable, sort, direction);

		AuctionCommand.SearchAuction searchCommand =
			auctionDtoMapper.toCommand(searchRequest, auctionStatus, categoryId, memberId);

		Page<AuctionInfo.SearchAuction> searchInfo = auctionFacade.searchAuctions(searchCommand, sortPage);

		Page<AuctionDto.AuctionSearchResponse> response = auctionDtoMapper.toSearchResponse(searchInfo);

		ResultDto<Page<AuctionDto.AuctionSearchResponse>> result = ResultDto.res(
			AUCTION_LIST_ACCESS_SUCCESS.getStatusCode(),
			AUCTION_LIST_ACCESS_SUCCESS.getDescription(),
			response
		);

		return ResponseEntity
			.status(AUCTION_LIST_ACCESS_SUCCESS.getHttpStatus())
			.body(result);
	}

	@Override
	@GetMapping("/{auction_id}")
	public ResponseEntity<ResultDto<AuctionDto.AuctionDetailsResponse>> auctionDetails(
		@PathVariable("auction_id") Long auctionId
	) {

		log.info("Received auction details request: {}", auctionId);

		AuctionCommand.RetrieveAuction auctionCommand = auctionDtoMapper.toCommand(auctionId);
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
			auctionDtoMapper.toRemoveCommand(auctionId, userPrincipal.getId());

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
	@PostMapping("/likes/{auction_id}")
	public ResponseEntity<ResultDto<AuctionDto.AuctionLikeResponse>> auctionLike(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@PathVariable("auction_id") Long auctionId
	) {
		log.info("Received auction like request: {}, memberId: {}", auctionId, userPrincipal.getId());

		AuctionCommand.LikeAuction likeCommand =
			auctionDtoMapper.toLikeCommand(auctionId, userPrincipal.getId());

		AuctionInfo.LikeAuction likeInfo = auctionFacade.likeAuction(likeCommand);

		AuctionDto.AuctionLikeResponse response = auctionDtoMapper.toLikeResponse(likeInfo);

		log.info("Auction like request finished: auctionId: {}, memberId: {}", auctionId, userPrincipal.getId());

		if ("LIKE".equals(response.likeType())) {
			ResultDto<AuctionDto.AuctionLikeResponse> result = ResultDto.res(
				AUCTION_LIKE_SUCCESS.getStatusCode(),
				AUCTION_LIKE_SUCCESS.getDescription(),
				response
			);
			return ResponseEntity
				.status(AUCTION_LIKE_SUCCESS.getHttpStatus())
				.body(result);
		} else {
			ResultDto<AuctionDto.AuctionLikeResponse> result = ResultDto.res(
				AUCTION_UNLIKE_SUCCESS.getStatusCode(),
				AUCTION_UNLIKE_SUCCESS.getDescription(),
				response
			);
			return ResponseEntity
				.status(AUCTION_UNLIKE_SUCCESS.getHttpStatus())
				.body(result);

		}
	}

	@Override
	@GetMapping("/likes")
	public ResponseEntity<ResultDto<Page<AuctionDto.AuctionLikeListResponse>>> myAuctionLikeList(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@RequestParam(value = "sort", defaultValue = "createdAt") String sort,
		@RequestParam(value = "direction", defaultValue = "DESC") String direction,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size
	) {
		Long memberId = userPrincipal.getId();

		log.info("Received auction like list request. memberId : {}", memberId);

		Pageable sortPage = pageSort.sortPage(PageRequest.of(page, size), sort, direction);

		Page<AuctionInfo.RetrieveMyAuctionLikes> likeInfo = auctionFacade.retrieveMyAuctionLikes(memberId, sortPage);

		Page<AuctionDto.AuctionLikeListResponse> response = auctionDtoMapper.toLikeListResponse(likeInfo);

		ResultDto<Page<AuctionDto.AuctionLikeListResponse>> result = ResultDto.res(
			AUCTION_LIKE_LIST_SUCCESS.getStatusCode(),
			AUCTION_LIKE_LIST_SUCCESS.getDescription(),
			response
		);

		log.info("Auction like list retrieved memberId : {}", memberId);

		return ResponseEntity
			.status(AUCTION_LIKE_LIST_SUCCESS.getHttpStatus())
			.body(result);

	}

	@Override
	@GetMapping("/me")
	public ResponseEntity<ResultDto<Page<AuctionDto.MyAuctionListResponse>>> myAuctionList(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@RequestParam(value = "auctionStatus", required = false) Auction.AuctionStatus auctionStatus,
		@RequestParam(value = "sort", defaultValue = "createdAt") String sort,
		@RequestParam(value = "direction", defaultValue = "DESC") String direction,
		@PageableDefault(page = 0, size = 10)
		Pageable pageable
	) {

		Long memberId = userPrincipal.getId();

		Pageable sortPage = pageSort.sortPage(pageable, sort, direction);

		AuctionCommand.RetrieveMyAuctions retrieveMyAuctionsCommand =
			auctionDtoMapper.toMyCommand(memberId, auctionStatus);

		Page<AuctionInfo.RetrieveMyAuctions> retrieveMyAuctionsInfo =
			auctionFacade.retrieveMyAuctions(retrieveMyAuctionsCommand, sortPage);

		Page<AuctionDto.MyAuctionListResponse> myAuctionListResponse =
			auctionDtoMapper.toMyAuctionListResponse(retrieveMyAuctionsInfo);

		ResultDto<Page<AuctionDto.MyAuctionListResponse>> resultDto = ResultDto.res(
			MY_AUCTION_LIST_SUCCESS.getStatusCode(),
			MY_AUCTION_LIST_SUCCESS.getDescription(),
			myAuctionListResponse
		);

		return ResponseEntity
			.status(MY_AUCTION_LIST_SUCCESS.getHttpStatus())
			.body(resultDto);
	}

	@Override
	@GetMapping("/member/{member_id}")
	public ResponseEntity<ResultDto<Page<AuctionDto.MemberAuctionListResponse>>> memberAuctionList(
		@PathVariable("member_id") Long memberId,
		@RequestParam(value = "auctionStatus", required = false) Auction.AuctionStatus auctionStatus,
		@RequestParam(value = "sort", defaultValue = "createdAt") String sort,
		@RequestParam(value = "direction", defaultValue = "DESC") String direction,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size
	) {

		Pageable sortPage = pageSort.sortPage(PageRequest.of(page, size), sort, direction);

		AuctionCommand.RetrieveMemberAuctions memberCommand =
			auctionDtoMapper.toMemberCommand(memberId, auctionStatus);

		Page<AuctionInfo.RetrieveMemberAuctions> memberInfo =
			auctionFacade.retrieveMemberAuctions(memberCommand, sortPage);

		Page<AuctionDto.MemberAuctionListResponse> response =
			auctionDtoMapper.toMemberAuctionListResponse(memberInfo);

		ResultDto<Page<AuctionDto.MemberAuctionListResponse>> result = ResultDto.res(
			MEMBER_AUCTION_LIST_SUCCESS.getStatusCode(),
			MEMBER_AUCTION_LIST_SUCCESS.getDescription(),
			response
		);

		return ResponseEntity
			.status(MEMBER_AUCTION_LIST_SUCCESS.getHttpStatus())
			.body(result);
	}

	@Override
	@GetMapping("/bid/me")
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
