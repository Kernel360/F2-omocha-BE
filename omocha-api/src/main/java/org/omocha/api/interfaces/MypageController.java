package org.omocha.api.interfaces;

import static org.omocha.domain.exception.code.SuccessCode.*;

import org.omocha.api.application.MypageFacade;
import org.omocha.api.common.auth.jwt.UserPrincipal;
import org.omocha.api.common.response.ResultDto;
import org.omocha.api.common.util.PasswordManager;
import org.omocha.api.interfaces.dto.MypageDto;
import org.omocha.api.interfaces.mapper.MypageDtoMapper;
import org.omocha.domain.auction.Auction;
import org.omocha.domain.auction.AuctionCommand;
import org.omocha.domain.auction.AuctionInfo;
import org.omocha.domain.auction.bid.BidCommand;
import org.omocha.domain.auction.bid.BidInfo;
import org.omocha.domain.common.util.PageSort;
import org.omocha.domain.member.MemberCommand;
import org.omocha.domain.member.MemberInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping("/api/v2/my-info")
public class MypageController implements MypageApi {

	private final MypageFacade mypageFacade;
	private final MypageDtoMapper mypageDtoMapper;
	private final PasswordManager passwordManager;
	private final PageSort pageSort;

	// TODO : 멤버 정보 반환? 고민해야됨
	//		로그인시 or Api, + 회원 정보 추가
	@Override
	@GetMapping("/me")
	public ResponseEntity<ResultDto<MypageDto.CurrentMemberInfoResponse>> currentMemberInfo(
		@AuthenticationPrincipal UserPrincipal userPrincipal
	) {

		log.info("getMe started");

		Long memberId = userPrincipal.getId();

		log.debug("get me getId {}", userPrincipal.getId());

		MemberInfo.RetrieveCurrentMemberInfo memberInfoResponse = mypageFacade.retrieveCurrentMemberInfo(memberId);

		MypageDto.CurrentMemberInfoResponse currentMemberInfoResponse = mypageDtoMapper.toResponse(memberInfoResponse);

		ResultDto<MypageDto.CurrentMemberInfoResponse> resultDto = ResultDto.res(
			MEMBER_INFO_RETRIEVE_SUCCESS.getStatusCode(),
			MEMBER_INFO_RETRIEVE_SUCCESS.getDescription(),
			currentMemberInfoResponse
		);

		log.info("getMe finished");
		log.debug("get me resultDto {}", resultDto);

		return ResponseEntity
			.status(MEMBER_INFO_RETRIEVE_SUCCESS.getHttpStatus())
			.body(resultDto);

	}

	@Override
	@PatchMapping(value = "/profile-image",
		consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<ResultDto<MypageDto.ProfileImageModifyResponse>> profileImageModify(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@RequestPart(value = "profileImage", required = true) MultipartFile profileImage
	) {

		log.info("memberProfileImageModify started");
		log.debug("memberProfileImageModify profileImage {}", profileImage);

		Long memberId = userPrincipal.getId();

		MemberCommand.ModifyProfileImage modifyProfileImageCommand = mypageDtoMapper.toCommand(memberId, profileImage);

		MemberInfo.modifyProfileImage modifyProfileImageInfo = mypageFacade.modifyProfileImage(
			modifyProfileImageCommand);

		MypageDto.ProfileImageModifyResponse profileImageResponse = mypageDtoMapper.toResponse(modifyProfileImageInfo);

		ResultDto<MypageDto.ProfileImageModifyResponse> resultDto = ResultDto.res(
			PROFILE_IMAGE_UPDATED.getStatusCode(),
			PROFILE_IMAGE_UPDATED.getDescription(),
			profileImageResponse
		);

		log.info("memberProfileImageModify finished");
		log.debug("memberProfileImageModify resultDto {}", resultDto);

		return ResponseEntity
			.status(PROFILE_IMAGE_UPDATED.getHttpStatus())
			.body(resultDto);
	}

	@Override
	@PatchMapping("/password")
	public ResponseEntity<ResultDto<Void>> passwordModify(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@RequestBody MypageDto.PasswordModifyRequest passwordModifyRequest
	) {

		log.info("passwordModify started");
		log.debug("passwordModify passwordModifyRequest {}", passwordModifyRequest);

		Long memberId = userPrincipal.getId();

		MemberCommand.ModifyPassword modifyPasswordCommand = mypageDtoMapper.toCommand(
			memberId,
			passwordModifyRequest.currentPassword(),
			passwordManager.encrypt(passwordModifyRequest.newPassword())
		);

		mypageFacade.modifyPassword(modifyPasswordCommand);

		ResultDto<Void> resultDto = ResultDto.res(
			PASSWORD_UPDATED.getStatusCode(),
			PASSWORD_UPDATED.getDescription()
		);

		log.info("passwordModify finished");
		log.debug("passwordModify resultDto {}", resultDto);

		return ResponseEntity
			.status(PASSWORD_UPDATED.getHttpStatus())
			.body(resultDto);

	}

	// TODO : 사용자 정보 수정
	@Override
	@PatchMapping("/basic-info")
	public ResponseEntity<ResultDto<MypageDto.MemberModifyResponse>> memberInfoModify(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@RequestBody MypageDto.MemberModifyRequest memberModifyRequest
	) {

		log.info("memberInfoModify started");
		log.debug("memberInfoModify request {}", memberModifyRequest);

		Long memberId = userPrincipal.getId();

		MemberCommand.ModifyBasicInfo modifyBasicInfoCommand = mypageDtoMapper.toCommand(memberId,
			memberModifyRequest);

		MemberInfo.ModifyBasicInfo modifyBasicInfo = mypageFacade.modifyBasicInfo(modifyBasicInfoCommand);

		MypageDto.MemberModifyResponse memberModifyResponse = mypageDtoMapper.toResponse(modifyBasicInfo);

		ResultDto<MypageDto.MemberModifyResponse> resultDto = ResultDto.res(
			MEMBER_INFO_UPDATED.getStatusCode(),
			MEMBER_INFO_UPDATED.getDescription(),
			memberModifyResponse
		);

		log.info("memberInfoModify finished");
		log.debug("memberInfoModify resultDto {}", resultDto);

		return ResponseEntity
			.status(MEMBER_INFO_UPDATED.getHttpStatus())
			.body(resultDto);

	}

	// TODO : 키워드 관련 추가 예정
	@Override
	@GetMapping("/histories/auctions")
	public ResponseEntity<ResultDto<Page<MypageDto.MyAuctionListResponse>>> myAuctionList(
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

		AuctionCommand.RetrieveMyAuctions retrieveMyAuctionsCommand = mypageDtoMapper.toCommand(
			memberId,
			auctionStatus
		);

		Page<AuctionInfo.RetrieveMyAuctions> retrieveMyAuctionsInfo = mypageFacade.retrieveMyAuctions(
			retrieveMyAuctionsCommand,
			sortPage
		);

		Page<MypageDto.MyAuctionListResponse> myAuctionListResponse = mypageDtoMapper.toMyAuctionListResponse(
			retrieveMyAuctionsInfo
		);

		ResultDto<Page<MypageDto.MyAuctionListResponse>> resultDto = ResultDto.res(
			MY_AUCTION_LIST_SUCCESS.getStatusCode(),
			MY_AUCTION_LIST_SUCCESS.getDescription(),
			myAuctionListResponse
		);

		return ResponseEntity
			.status(MY_AUCTION_LIST_SUCCESS.getHttpStatus())
			.body(resultDto);

	}

	@Override
	@GetMapping("/histories/bids")
	public ResponseEntity<ResultDto<Page<MypageDto.MyBidAuctionResponse>>> myBidAuctionList(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@RequestParam(value = "sort", defaultValue = "createdAt") String sort,
		@RequestParam(value = "direction", defaultValue = "DESC") String direction,
		@PageableDefault(page = 0, size = 10)
		Pageable pageable
	) {

		log.info("myBidAuctionList started memberId : {} ", userPrincipal.getId());

		Long memberId = userPrincipal.getId();

		Pageable sortPage = pageSort.sortPage(pageable, sort, direction);

		BidCommand.RetrieveMyBidAuctions retrieveMyBidAuctionsCommand = mypageDtoMapper.toBidAuctionCommand(memberId);

		Page<BidInfo.RetrieveMyBidAuctions> retrieveMyBidAuctionsInfo = mypageFacade.retrieveMyBidAuctions(
			retrieveMyBidAuctionsCommand,
			sortPage
		);

		Page<MypageDto.MyBidAuctionResponse> myBidAuctionListResponse = mypageDtoMapper.toMyBiductionListResponse(
			retrieveMyBidAuctionsInfo);

		ResultDto<Page<MypageDto.MyBidAuctionResponse>> resultDto = ResultDto.res(
			MY_BIDDING_AUCTION_LIST_SUCCESS.getStatusCode(),
			MY_BIDDING_AUCTION_LIST_SUCCESS.getDescription(),
			myBidAuctionListResponse
		);

		log.info("myBidAuctionList finished");

		return ResponseEntity
			.status(MY_BIDDING_AUCTION_LIST_SUCCESS.getHttpStatus())
			.body(resultDto);

	}

	@Override
	@GetMapping("/histories/bids/{auction_id}")
	public ResponseEntity<ResultDto<Page<MypageDto.MyBidListResponse>>> myBidList(
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

		BidCommand.RetrieveMyBids retrieveMyBidsCommand = mypageDtoMapper.toCommand(memberId, auctionId);

		Page<BidInfo.RetrieveMyBids> retrieveMyBidsInfo = mypageFacade.retrieveMyBids(retrieveMyBidsCommand,
			sortPage);

		Page<MypageDto.MyBidListResponse> myBidListResponse = mypageDtoMapper.toMyBidListResponse(
			retrieveMyBidsInfo);

		ResultDto<Page<MypageDto.MyBidListResponse>> resultDto = ResultDto.res(
			MY_BIDDING_LIST_SUCCESS.getStatusCode(),
			MY_BIDDING_LIST_SUCCESS.getDescription(),
			myBidListResponse
		);

		log.info("myBidList finished");

		return ResponseEntity
			.status(MY_BIDDING_LIST_SUCCESS.getHttpStatus())
			.body(resultDto);

	}
}
