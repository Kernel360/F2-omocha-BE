package org.omocha.api.interfaces;

import static org.omocha.domain.exception.code.SuccessCode.*;

import org.omocha.api.application.MemberFacade;
import org.omocha.api.common.auth.jwt.UserPrincipal;
import org.omocha.api.common.response.ResultDto;
import org.omocha.api.common.util.PasswordManager;
import org.omocha.api.interfaces.dto.MemberDto;
import org.omocha.api.interfaces.mapper.MemberDtoMapper;
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
	@GetMapping()
	public ResponseEntity<ResultDto<MemberDto.CurrentMemberInfoResponse>> currentMemberInfo(
		@AuthenticationPrincipal UserPrincipal userPrincipal
	) {

		log.info("getMe started");

		Long memberId = userPrincipal.getId();

		log.debug("get me getId {}", userPrincipal.getId());

		MemberInfo.RetrieveCurrentMemberInfo memberInfoResponse = memberFacade.retrieveCurrentMemberInfo(memberId);

		MemberDto.CurrentMemberInfoResponse currentMemberInfoResponse = memberDtoMapper.toResponse(memberInfoResponse);

		ResultDto<MemberDto.CurrentMemberInfoResponse> resultDto = ResultDto.res(
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

	@PatchMapping(value = "/profile-image",
		consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<ResultDto<MemberDto.ProfileImageModifyResponse>> profileImageModify(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@RequestPart(value = "profileImage", required = true) MultipartFile profileImage
	) {

		log.info("memberProfileImageModify started");
		log.debug("memberProfileImageModify profileImage {}", profileImage);

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

		log.info("memberProfileImageModify finished");
		log.debug("memberProfileImageModify resultDto {}", resultDto);

		return ResponseEntity
			.status(PROFILE_IMAGE_UPDATED.getHttpStatus())
			.body(resultDto);
	}

	@PatchMapping("/password")
	public ResponseEntity<ResultDto<Void>> passwordModify(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@RequestBody MemberDto.PasswordModifyRequest passwordModifyRequest
	) {

		log.info("passwordModify started");
		log.debug("passwordModify passwordModifyRequest {}", passwordModifyRequest);

		Long memberId = userPrincipal.getId();

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
		log.debug("passwordModify resultDto {}", resultDto);

		return ResponseEntity
			.status(PASSWORD_UPDATED.getHttpStatus())
			.body(resultDto);

	}

	// TODO : 사용자 정보 수정
	@PatchMapping()
	public ResponseEntity<ResultDto<MemberDto.MemberModifyResponse>> memberInfoModify(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@RequestBody MemberDto.MemberModifyRequest memberModifyRequest
	) {

		log.info("memberInfoModify started");
		log.debug("memberInfoModify request {}", memberModifyRequest);

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
		log.debug("memberInfoModify resultDto {}", resultDto);

		return ResponseEntity
			.status(MEMBER_INFO_UPDATED.getHttpStatus())
			.body(resultDto);

	}

	// TODO : 키워드 관련 추가 예정

}
