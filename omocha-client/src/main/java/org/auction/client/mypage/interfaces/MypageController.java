package org.auction.client.mypage.interfaces;

import org.auction.client.common.code.MypageCode;
import org.auction.client.common.dto.ResultDto;
import org.auction.client.jwt.UserPrincipal;
import org.auction.client.mypage.application.MypageService;
import org.auction.client.mypage.interfaces.response.MemberInfoResponse;
import org.auction.client.mypage.interfaces.response.MypageAuctionListResponse;
import org.auction.client.mypage.interfaces.response.MypageBidListResponse;
import org.auction.domain.auction.domain.enums.AuctionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/myinfo")
public class MypageController implements MypageApi {

	private final MypageService mypageService;

	// TODO : 멤버 정보 반환? 고민해야됨
	//		로그인시 or Api, + 회원 정보 추가
	@GetMapping("/me")
	public ResponseEntity<ResultDto<MemberInfoResponse>> getMe(
		@AuthenticationPrincipal UserPrincipal userPrincipal
	) {

		Long memberId = userPrincipal.getId();

		MemberInfoResponse memberInfoResponse = mypageService.findMe(memberId);

		ResultDto<MemberInfoResponse> resultDto = ResultDto.res(
			MypageCode.MEMBER_INFO_RETRIEVE_SUCCESS.getStatusCode(),
			MypageCode.MEMBER_INFO_RETRIEVE_SUCCESS.getResultMsg()
			, memberInfoResponse
		);

		return ResponseEntity
			.status(MypageCode.MEMBER_INFO_RETRIEVE_SUCCESS.getHttpStatus())
			.body(resultDto);

	}

	// TODO : 사용자 정보 수정
	@PatchMapping()
	public void UserInfoModify(
		@AuthenticationPrincipal UserPrincipal userPrincipal
	) {

	}

	// TODO : 키워드 관련 추가 예정

	@GetMapping("/transaction/auction")
	public ResponseEntity<ResultDto<Page<MypageAuctionListResponse>>> TransactionAuctionList(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@RequestParam(value = "auctionStatus", required = false) AuctionStatus auctionStatus,
		@RequestParam(value = "sort", defaultValue = "createdAt") String sort,
		@RequestParam(value = "direction", defaultValue = "DESC") String direction,
		@PageableDefault(page = 0, size = 10)
		Pageable pageable
	) {

		log.info("getTransactionAuction");
		log.debug("auctionStatus: {}", auctionStatus);

		Long memberId = userPrincipal.getId();

		Sort.Direction sortDirection = direction.equalsIgnoreCase("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;
		pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(sortDirection, sort));

		Page<MypageAuctionListResponse> auctionListResponses = mypageService.findTransactionAuctionList(memberId,
			auctionStatus,
			pageable);

		ResultDto<Page<MypageAuctionListResponse>> resultDto = ResultDto.res(
			MypageCode.TRANSACTION_AUCTION_LIST_SUCCESS.getStatusCode(),
			MypageCode.TRANSACTION_AUCTION_LIST_SUCCESS.getResultMsg(),
			auctionListResponses
		);

		log.debug("auctionListResponses: {}", auctionListResponses);
		log.debug("resultDto: {}", resultDto);

		return ResponseEntity
			.status(MypageCode.TRANSACTION_AUCTION_LIST_SUCCESS.getHttpStatus())
			.body(resultDto);

	}

	@GetMapping("/transaction/bid")
	public ResponseEntity<ResultDto<Page<MypageBidListResponse>>> TransactionBidList(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@RequestParam(value = "sort", defaultValue = "createdAt") String sort,
		@RequestParam(value = "direction", defaultValue = "DESC") String direction,
		@PageableDefault(page = 0, size = 10)
		Pageable pageable
	) {

		log.info("getTransactionBid called");

		log.debug("pageable: {}", pageable);

		Long memberId = userPrincipal.getId();

		Sort.Direction sortDirection = direction.equalsIgnoreCase("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;
		pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(sortDirection, sort));

		Page<MypageBidListResponse> auctionListResponses = mypageService.findTransactionBidList(memberId, pageable);

		ResultDto<Page<MypageBidListResponse>> resultDto = ResultDto.res(
			MypageCode.TRANSACTION_BIDDING_LIST_SUCCESS.getStatusCode(),
			MypageCode.TRANSACTION_BIDDING_LIST_SUCCESS.getResultMsg(),
			auctionListResponses
		);

		return ResponseEntity
			.status(MypageCode.TRANSACTION_BIDDING_LIST_SUCCESS.getHttpStatus())
			.body(resultDto);

	}
}
