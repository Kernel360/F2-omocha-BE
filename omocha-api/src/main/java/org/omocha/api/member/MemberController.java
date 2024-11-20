package org.omocha.api.member;

import static org.omocha.domain.common.code.SuccessCode.*;

import org.omocha.api.auth.jwt.UserPrincipal;
import org.omocha.api.common.response.ResultDto;
import org.omocha.api.common.util.PasswordManager;
import org.omocha.api.member.dto.MemberDto;
import org.omocha.api.member.dto.MemberDtoMapper;
import org.omocha.domain.member.MemberCommand;
import org.omocha.domain.member.MemberInfo;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/member")
public class MemberController implements MemberApi {

	private final MemberFacade memberFacade;
	private final MemberDtoMapper memberDtoMapper;
	private final PasswordManager passwordManager;

	// TODO : 멤버 정보 반환? 고민해야됨
	//		로그인시 or Api, + 회원 정보 추가
	@Override
	@GetMapping()
	public ResponseEntity<ResultDto<MemberDto.CurrentMemberInfoResponse>> currentMemberInfo(
		@AuthenticationPrincipal UserPrincipal userPrincipal
	) {

		log.info("currentMemberInfo started memberId={}", userPrincipal.getId());

		Long memberId = userPrincipal.getId();

		MemberInfo.RetrieveCurrentMemberInfo memberInfoResponse = memberFacade.retrieveCurrentMemberInfo(memberId);

		MemberDto.CurrentMemberInfoResponse currentMemberInfoResponse = memberDtoMapper.toResponse(memberInfoResponse);

		ResultDto<MemberDto.CurrentMemberInfoResponse> resultDto = ResultDto.res(
			MEMBER_INFO_RETRIEVE_SUCCESS.getStatusCode(),
			MEMBER_INFO_RETRIEVE_SUCCESS.getDescription(),
			currentMemberInfoResponse
		);

		log.info("currentMemberInfo finished ");

		return ResponseEntity
			.status(MEMBER_INFO_RETRIEVE_SUCCESS.getHttpStatus())
			.body(resultDto);

	}

	@Override
	@PatchMapping(value = "/profile-image",
		consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<ResultDto<MemberDto.ProfileImageModifyResponse>> profileImageModify(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@RequestPart(value = "profileImage", required = true) MultipartFile profileImage
	) {

		log.info("profileImageModify started memberId={}", userPrincipal.getId());

		Long memberId = userPrincipal.getId();

		MemberCommand.ModifyProfileImage modifyProfileImageCommand = memberDtoMapper.toCommand(memberId, profileImage);

		MemberInfo.ModifyProfileImage modifyProfileImageInfo = memberFacade.modifyProfileImage(
			modifyProfileImageCommand);

		MemberDto.ProfileImageModifyResponse profileImageResponse = memberDtoMapper.toResponse(modifyProfileImageInfo);

		ResultDto<MemberDto.ProfileImageModifyResponse> resultDto = ResultDto.res(
			PROFILE_IMAGE_UPDATED.getStatusCode(),
			PROFILE_IMAGE_UPDATED.getDescription(),
			profileImageResponse
		);

		log.info("profileImageModify finished");

		return ResponseEntity
			.status(PROFILE_IMAGE_UPDATED.getHttpStatus())
			.body(resultDto);
	}

	@Override
	@PatchMapping("/password")
	public ResponseEntity<ResultDto<Void>> passwordModify(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@RequestBody MemberDto.PasswordModifyRequest passwordModifyRequest
	) {

		log.info("passwordModify started memberId={}", userPrincipal.getId());

		Long memberId = userPrincipal.getId();

		passwordManager.validateIdenticalPassword(
			passwordModifyRequest.currentPassword(),
			passwordModifyRequest.newPassword(),
			memberId
		);

		MemberCommand.ModifyPassword modifyPasswordCommand = memberDtoMapper.toCommand(
			memberId,
			passwordModifyRequest.currentPassword(),
			passwordManager.encrypt(passwordModifyRequest.newPassword())
		);

		memberFacade.modifyPassword(modifyPasswordCommand);

		ResultDto<Void> resultDto = ResultDto.res(
			PASSWORD_UPDATED.getStatusCode(),
			PASSWORD_UPDATED.getDescription()
		);

		log.info("passwordModify finished");

		return ResponseEntity
			.status(PASSWORD_UPDATED.getHttpStatus())
			.body(resultDto);

	}

	// TODO : 사용자 정보 수정
	@Override
	@PatchMapping()
	public ResponseEntity<ResultDto<MemberDto.MemberModifyResponse>> memberInfoModify(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@RequestBody MemberDto.MemberModifyRequest memberModifyRequest
	) {

		log.info("memberInfoModify started memberId={} , memberModifyRequest={}", userPrincipal.getId(),
			memberModifyRequest);

		Long memberId = userPrincipal.getId();

		MemberCommand.ModifyBasicInfo modifyBasicInfoCommand = memberDtoMapper.toCommand(memberId,
			memberModifyRequest);

		MemberInfo.ModifyBasicInfo modifyBasicInfo = memberFacade.modifyBasicInfo(modifyBasicInfoCommand);

		MemberDto.MemberModifyResponse memberModifyResponse = memberDtoMapper.toResponse(modifyBasicInfo);

		ResultDto<MemberDto.MemberModifyResponse> resultDto = ResultDto.res(
			MEMBER_INFO_UPDATED.getStatusCode(),
			MEMBER_INFO_UPDATED.getDescription(),
			memberModifyResponse
		);

		log.info("memberInfoModify finished");

		return ResponseEntity
			.status(MEMBER_INFO_UPDATED.getHttpStatus())
			.body(resultDto);

	}

}
