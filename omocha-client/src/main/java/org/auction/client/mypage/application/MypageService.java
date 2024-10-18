package org.auction.client.mypage.application;

import java.util.List;
import java.util.stream.Collectors;

import org.auction.client.bid.application.BidService;
import org.auction.client.common.code.MemberCode;
import org.auction.client.common.code.MypageCode;
import org.auction.client.exception.member.InvalidPasswordException;
import org.auction.client.exception.member.MemberNotFoundException;
import org.auction.client.exception.mypage.MypageInvalidEmailFormatException;
import org.auction.client.exception.mypage.MypageInvalidPasswordFormatException;
import org.auction.client.exception.mypage.MypageInvalidPhoneNumberException;
import org.auction.client.exception.mypage.MypageNicknameDuplicateException;
import org.auction.client.image.application.AwsS3Service;
import org.auction.client.member.application.MemberService;
import org.auction.client.mypage.interfaces.request.MemberModifyRequest;
import org.auction.client.mypage.interfaces.request.PasswordModifyReuqest;
import org.auction.client.mypage.interfaces.response.MemberInfoResponse;
import org.auction.client.mypage.interfaces.response.MemberModifyResponse;
import org.auction.client.mypage.interfaces.response.MypageAuctionListResponse;
import org.auction.client.mypage.interfaces.response.MypageBidListResponse;
import org.auction.client.mypage.interfaces.response.ProfileImageResponse;
import org.auction.domain.auction.domain.entity.AuctionEntity;
import org.auction.domain.auction.domain.enums.AuctionStatus;
import org.auction.domain.auction.infrastructure.AuctionRepository;
import org.auction.domain.bid.entity.BidEntity;
import org.auction.domain.bid.infrastructure.BidRepository;
import org.auction.domain.image.domain.entity.ImageEntity;
import org.auction.domain.member.domain.entity.MemberEntity;
import org.auction.domain.member.infrastructure.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MypageService {

	private final AuctionRepository auctionRepository;
	private final MemberRepository memberRepository;
	private final BidRepository bidRepository;
	private final BidService bidService;
	private final MemberService memberService;
	private final PasswordEncoder passwordEncoder;
	private final AwsS3Service awsS3Service;

	@Transactional(readOnly = true)
	public MemberInfoResponse findMe(
		Long memberId
	) {

		log.debug("find me start for member {}", memberId);

		// TODO : 개선 필요(서버측 문제?) , Exception
		MemberEntity memberEntity = memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberNotFoundException(MemberCode.MEMBER_NOT_FOUND));

		MemberInfoResponse memberInfoResponse = MemberInfoResponse.toDto(memberEntity);

		log.debug("find me finished for member {}", memberId);

		return memberInfoResponse;

	}

	@Transactional(readOnly = true)
	public Page<MypageAuctionListResponse> findMyAuctionList(
		Long memberId,
		AuctionStatus auctionStatus,
		Pageable pageable
	) {

		log.debug("find transaction auction list start for member {}", memberId);

		MemberEntity memberEntity = memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberNotFoundException(MemberCode.MEMBER_NOT_FOUND));

		// TODO : 이미지 갯수 논의 필요
		Page<AuctionEntity> myAuctionList = auctionRepository
			.searchMyAuctionList(memberEntity.getMemberId(), auctionStatus, pageable);

		// DTO로 변환
		Page<MypageAuctionListResponse> content = myAuctionList.map(auction -> {
			List<String> imageKeys = auction.getImages().stream()
				.map(ImageEntity::getS3Key)
				.collect(Collectors.toList());

			return new MypageAuctionListResponse(
				auction.getAuctionId(),
				auction.getTitle(),
				auction.getAuctionStatus(),
				bidService.getCurrentHighestBidPrice(auction.getAuctionId()),
				auction.getEndDate(),
				imageKeys
			);
		});

		log.debug("get transaction auction list finish for member {}", memberId);

		return content;
	}

	@Transactional(readOnly = true)
	public Page<MypageBidListResponse> findMyBidList(
		Long memberId,
		Pageable pageable
	) {

		log.debug("get transaction bid list start for member {}", memberId);

		MemberEntity memberEntity = memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberNotFoundException(MemberCode.MEMBER_NOT_FOUND));

		// TODO : 이미지 갯수 논의 필요
		Page<BidEntity> bidPageList = bidRepository.searchMyBidList(memberEntity.getMemberId(), pageable);

		// DTO로 변환
		Page<MypageBidListResponse> content = bidPageList.map(auction -> {
			List<String> imageKeys = auction.getAuctionEntity().getImages().stream()
				.map(ImageEntity::getS3Key)
				.collect(Collectors.toList());
			return new MypageBidListResponse(
				auction.getAuctionEntity().getAuctionId(),
				auction.getAuctionEntity().getTitle(),
				auction.getBidPrice(),
				auction.getAuctionEntity().getEndDate(),
				imageKeys
			);

		});

		log.debug("get transaction bid finish list for member {}", memberId);

		return content;

	}

	@Transactional
	public MemberModifyResponse modifyBasicInfoMember(
		Long memberId,
		MemberModifyRequest memberModifyRequest
	) {

		log.debug("modify member start for member {}", memberId);

		MemberEntity memberEntity = memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberNotFoundException(MemberCode.MEMBER_NOT_FOUND));

		validateFormat(
			memberModifyRequest.email(),
			memberModifyRequest.phoneNumber()
		);

		validateUniqueNickname(memberModifyRequest.nickName());

		validatePassword(memberModifyRequest.password(), memberEntity);

		memberEntity.updateMember(
			memberModifyRequest.password(),
			memberModifyRequest.nickName(),
			memberModifyRequest.email(),
			memberModifyRequest.phoneNumber()
		);

		log.debug("modify member finished for member {}", memberId);

		return MemberModifyResponse.toDto(memberEntity);

	}

	@Transactional
	public void modifyPassword(
		Long memberId,
		PasswordModifyReuqest passwordModifyReuqest
	) {

		log.debug("modify password start for member {}", memberId);

		validatePasswordFormat(passwordModifyReuqest.newPassword());

		MemberEntity memberEntity = memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberNotFoundException(MemberCode.MEMBER_NOT_FOUND));

		validatePassword(passwordModifyReuqest.oldPassword(), memberEntity);

		memberEntity.updatePassword(passwordEncoder.encode(passwordModifyReuqest.newPassword()));

		log.debug("modify password finished for member {}", memberId);
	}

	// 유저 정보에 s3key가 있으면 삭제 후 업로드 없으면 업로드
	@Transactional
	public ProfileImageResponse modifyProfileImage(
		Long memberId,
		MultipartFile profileImage
	) {

		log.debug("modify profile image start for member {}", memberId);

		String s3Key;

		MemberEntity memberEntity = memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberNotFoundException(MemberCode.MEMBER_NOT_FOUND));

		if (memberEntity.getProfileImageUrl() == null) {
			s3Key = uploadProfileImage(profileImage);
		} else {
			awsS3Service.deleteFile(memberEntity.getProfileImageUrl());
			s3Key = uploadProfileImage(profileImage);
		}

		memberEntity.updateProfileImage(s3Key);

		log.debug("modify profile image finished for member {}", memberId);

		return ProfileImageResponse.toDto(s3Key);

	}

	public String uploadProfileImage(
		MultipartFile profileImage
	) {

		return awsS3Service.uploadFile(profileImage);

	}

	public void validateFormat(
		String email,
		String phoneNumber
	) {

		validateEmailFormat(email);
		validatePhoneNumberFormat(phoneNumber);
	}

	public void validateUniqueNickname(
		String nickName
	) {

		if (memberRepository.existsByNickname(nickName)) {
			throw new MypageNicknameDuplicateException(MypageCode.NICKNAME_DUPLICATE);
		}

	}

	public void validateEmailFormat(
		String email
	) {
		if (!email.matches("^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$")) {
			throw new MypageInvalidEmailFormatException(MypageCode.INVALID_EMAIL_FORMAT);
		}
	}

	public void validatePhoneNumberFormat(
		String phoneNumber
	) {
		if (phoneNumber.matches("01[0-9]-(\\d{4})-(\\d{4})|01[0-9]\\d{8}")) {
			throw new MypageInvalidPhoneNumberException(MypageCode.INVALID_PHONE_NUMBER_FORMAT);
		}

	}

	public void validatePasswordFormat(
		String newPassword
	) {
		// 8자리이상 영어,특문
		if (newPassword.matches("^(?=.*[a-zA-Z])(?=.*\\W).{8,}$")) {
			throw new MypageInvalidPasswordFormatException(MypageCode.INVALID_PASSWORD_FORMAT);

		}

	}

	public void validatePassword(
		String password,
		MemberEntity memberEntity
	) {
		if (passwordEncoder.matches(passwordEncoder.encode(password), memberEntity.getPassword())) {
			throw new InvalidPasswordException(MemberCode.INVALID_PASSWORD);
		}
	}

}
