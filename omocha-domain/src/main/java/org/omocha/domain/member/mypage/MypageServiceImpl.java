// package org.omocha.domain.member.mypage;
//
// import org.omocha.domain.auction.AuctionReader;
// import org.omocha.domain.image.ImageProvider;
// import org.omocha.domain.member.MemberReader;
// import org.springframework.stereotype.Service;
//
// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
//
// @Slf4j
// @Service
// @RequiredArgsConstructor
// public class MypageServiceImpl implements MypageService {
//
// 	// TODO : MypageService 를 Admin과 비슷하게 Api단만 만들어 놓고 서비스에 분리 시킬지 논의 필요
//
// 	private final AuctionReader auctionReader;
// 	private final MemberReader memberReader;
// 	private final ImageProvider imageProvider;
//
// 	// TODO : BIDREADER로 대체 해야함
// 	// private final BidRepository bidRepository;
// 	// private final BidService bidService;
//
// 	// 유저 정보에 s3key가 있으면 삭제 후 업로드 없으면 업로드
//
// 	// @Override
// 	// @Transactional(readOnly = true)
// 	// public Page<MypageInfo.MypageAuctionListResponse> findMyAuctionList(
// 	// 	Long memberId,
// 	// 	AuctionStatus auctionStatus,
// 	// 	Pageable pageable
// 	// ) {
// 	//
// 	// 	log.debug("find transaction auction list start for member {}", memberId);
// 	//
// 	// 	Member memberEntity = memberReader.findById(memberId);
// 	//
// 	// 	// TODO : 이미지 갯수 논의 필요
// 	// 	Page<Auction> myAuctionList = auctionReader.searchMyAuctionList(memberEntity.getMemberId(), auctionStatus,
// 	// 		pageable);
// 	//
// 	// 	// DTO로 변환
// 	// 	Page<MypageInfo.MypageAuctionListResponse> content = myAuctionList.map(auction -> {
// 	// 		List<String> imageKeys = auction.getImages().stream()
// 	// 			.map(Image::getImagePath)
// 	// 			.collect(Collectors.toList());
// 	//
// 	// 		return new MypageInfo.MypageAuctionListResponse(
// 	// 			auction.getAuctionId(),
// 	// 			auction.getTitle(),
// 	// 			auction.getAuctionStatus(),
// 	// 			bidService.getCurrentHighestBidPrice(auction.getAuctionId()),
// 	// 			auction.getEndDate(),
// 	// 			imageKeys
// 	// 		);
// 	// 	});
// 	//
// 	// 	log.debug("get transaction auction list finish for member {}", memberId);
// 	//
// 	// 	return content;
// 	// }
//
// 	// TODO : bid 기능 추가 후 수정
// 	// @Override
// 	// @Transactional(readOnly = true)
// 	// public Page<MypageBidListResponse> findMyBidList(Long memberId, Pageable pageable) {
// 	//
// 	// 	log.debug("get transaction bid list start for member {}", memberId);
// 	//
// 	// 	Member member = memberReader.findById(memberId);
// 	//
// 	// 	// TODO : 이미지 갯수 논의 필요
// 	// 	Page<BidEntity> bidPageList = bidRepository.searchMyBidList(memberEntity.getMemberId(), pageable);
// 	//
// 	// 	// DTO로 변환
// 	// 	Page<MypageBidListResponse> content = bidPageList.map(auction -> {
// 	// 		List<String> imageKeys = auction.getAuctionEntity().getImages().stream()
// 	// 			.map(ImageEntity::getS3Key)
// 	// 			.collect(Collectors.toList());
// 	// 		return new MypageBidListResponse(
// 	// 			auction.getAuctionEntity().getAuctionId(),
// 	// 			auction.getAuctionEntity().getTitle(),
// 	// 			auction.getBidPrice(),
// 	// 			auction.getAuctionEntity().getEndDate(),
// 	// 			imageKeys
// 	// 		);
// 	//
// 	// 	});
// 	//
// 	// 	log.debug("get transaction bid finish list for member {}", memberId);
// 	//
// 	// 	return content;
// 	//
// 	// }
//
// 	// public void validateFormat(
// 	// 	String email,
// 	// 	String phoneNumber
// 	// ) {
// 	//
// 	// 	validateEmailFormat(email);
// 	// 	validatePhoneNumberFormat(phoneNumber);
// 	// }
// 	//
// 	// public void validateUniqueNickname(
// 	// 	String nickName
// 	// ) {
// 	//
// 	// 	if (memberReader.existsByNickname(nickName)) {
// 	// 		throw new MypageNicknameDuplicateException(MypageCode.NICKNAME_DUPLICATE);
// 	// 	}
// 	//
// 	// }
// 	//
// 	// public void validateEmailFormat(
// 	// 	String email
// 	// ) {
// 	// 	if (!email.matches("^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$")) {
// 	// 		throw new MypageInvalidEmailFormatException(MypageCode.INVALID_EMAIL_FORMAT);
// 	// 	}
// 	// }
// 	//
// 	// public void validatePhoneNumberFormat(
// 	// 	String phoneNumber
// 	// ) {
// 	// 	if (phoneNumber.matches("01[0-9]-(\\d{4})-(\\d{4})|01[0-9]\\d{8}")) {
// 	// 		throw new MypageInvalidPhoneNumberException(MypageCode.INVALID_PHONE_NUMBER_FORMAT);
// 	// 	}
// 	//
// 	// }
// 	//
// 	// public void validatePasswordFormat(
// 	// 	String newPassword
// 	// ) {
// 	// 	// 8자리이상 영어,특문
// 	// 	if (newPassword.matches("^(?=.*[a-zA-Z])(?=.*\\W).{8,}$")) {
// 	// 		throw new MypageInvalidPasswordFormatException(MypageCode.INVALID_PASSWORD_FORMAT);
// 	//
// 	// 	}
// 	//
// 	// }
// 	//
//
// }
