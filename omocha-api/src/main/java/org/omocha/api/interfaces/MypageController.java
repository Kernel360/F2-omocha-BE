package org.omocha.api.interfaces;

import static org.omocha.domain.exception.code.MypageCode.*;

import org.omocha.api.application.MypageFacade;
import org.omocha.api.common.auth.jwt.UserPrincipal;
import org.omocha.api.common.response.ResultDto;
import org.omocha.api.interfaces.dto.MypageDto;
import org.omocha.api.interfaces.mapper.MypageDtoMapper;
import org.omocha.domain.member.mypage.MypageInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/myinfo")
public class MypageController {

	private final MypageFacade mypageFacade;
	private final MypageDtoMapper mypageDtoMapper;

	// TODO : 멤버 정보 반환? 고민해야됨
	//		로그인시 or Api, + 회원 정보 추가
	@GetMapping("/me")
	public ResponseEntity<ResultDto<MypageDto.MemberInfoResponse>> currentMemberInfo(
		@AuthenticationPrincipal UserPrincipal userPrincipal
	) {

		log.info("getMe started");

		Long memberId = userPrincipal.getId();

		log.debug("get me getId {}", userPrincipal.getId());

		MypageInfo.MemberInfoResponse mypageInfoResponse = mypageFacade.findCurrentMemberInfo(memberId);

		MypageDto.MemberInfoResponse mypageDtoResponse = mypageDtoMapper.of(mypageInfoResponse);

		ResultDto<MypageDto.MemberInfoResponse> resultDto = ResultDto.res(
			MEMBER_INFO_RETRIEVE_SUCCESS.getStatusCode(),
			MEMBER_INFO_RETRIEVE_SUCCESS.getResultMsg(),
			mypageDtoResponse
		);

		log.info("getMe finished");
		log.debug("get me resultDto {}", resultDto);

		return ResponseEntity
			.status(MEMBER_INFO_RETRIEVE_SUCCESS.getHttpStatus())
			.body(resultDto);

	}

	// TODO : 사용자 정보 수정
	// @PatchMapping()
	// public void userInfoModify(
	// 	@AuthenticationPrincipal UserPrincipal userPrincipal
	// ) {
	//
	// }

	// TODO : bid 추가 후 추가 수정
	// TODO : 키워드 관련 추가 예정
	// @GetMapping("/history/auction")
	// public ResponseEntity<ResultDto<Page<MypageAuctionListResponse>>> myAuctionList(
	// 	@AuthenticationPrincipal UserPrincipal userPrincipal,
	// 	@RequestParam(value = "auctionStatus", required = false) AuctionStatus auctionStatus,
	// 	@RequestParam(value = "sort", defaultValue = "createdAt") String sort,
	// 	@RequestParam(value = "direction", defaultValue = "DESC") String direction,
	// 	@PageableDefault(page = 0, size = 10)
	// 	Pageable pageable
	// ) {
	//
	// 	log.info("myAuctionList started");
	// 	log.debug("myAuctionList auctionStatus : {} , sort : {} , direction : {}", auctionStatus, sort, direction);
	//
	// 	Long memberId = userPrincipal.getId();
	//
	// 	Sort.Direction sortDirection = direction.equalsIgnoreCase("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;
	// 	pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(sortDirection, sort));
	//
	// 	Page<MypageAuctionListResponse> auctionListResponses = mypageFacade
	// 		.findMyAuctionList(memberId, auctionStatus, pageable);
	//
	// 	ResultDto<Page<MypageAuctionListResponse>> resultDto = ResultDto.res(
	// 		MypageCode.MY_AUCTION_LIST_SUCCESS.getStatusCode(),
	// 		MypageCode.MY_AUCTION_LIST_SUCCESS.getResultMsg(),
	// 		auctionListResponses
	// 	);
	//
	// 	log.info("myAuctionList finished");
	// 	log.debug("myAuctionList resultDto : {}", resultDto);
	//
	// 	return ResponseEntity
	// 		.status(MypageCode.MY_AUCTION_LIST_SUCCESS.getHttpStatus())
	// 		.body(resultDto);
	//
	// }

	// @GetMapping("/history/bid")
	// public ResponseEntity<ResultDto<Page<MypageBidListResponse>>> myBidList(
	// 	@AuthenticationPrincipal UserPrincipal userPrincipal,
	// 	@RequestParam(value = "sort", defaultValue = "createdAt") String sort,
	// 	@RequestParam(value = "direction", defaultValue = "DESC") String direction,
	// 	@PageableDefault(page = 0, size = 10)
	// 	Pageable pageable
	// ) {
	//
	// 	log.info("myBidList started");
	// 	log.debug("myBidList sort : {} , direction : {} , pageable : {} ", sort, direction, pageable);
	//
	// 	Long memberId = userPrincipal.getId();
	//
	// 	Sort.Direction sortDirection = direction.equalsIgnoreCase("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;
	// 	pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(sortDirection, sort));
	//
	// 	Page<MypageBidListResponse> auctionListResponses = mypageService.findMyBidList(memberId, pageable);
	//
	// 	ResultDto<Page<MypageBidListResponse>> resultDto = ResultDto.res(
	// 		MypageCode.MY_BIDDING_LIST_SUCCESS.getStatusCode(),
	// 		MypageCode.MY_BIDDING_LIST_SUCCESS.getResultMsg(),
	// 		auctionListResponses
	// 	);
	//
	// 	log.info("myBidList finished");
	// 	log.debug("myBidList resultDto : {} ", resultDto);
	//
	// 	return ResponseEntity
	// 		.status(MypageCode.MY_BIDDING_LIST_SUCCESS.getHttpStatus())
	// 		.body(resultDto);
	//
	// }
}
