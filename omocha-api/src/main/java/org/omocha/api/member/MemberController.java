package org.omocha.api.member;

import static org.omocha.domain.common.code.SuccessCode.*;

import org.omocha.api.auth.jwt.UserPrincipal;
import org.omocha.api.common.response.ResultDto;
import org.omocha.api.member.dto.MemberDto;
import org.omocha.api.member.dto.MemberDtoMapper;
import org.omocha.domain.member.MemberCommand;
import org.omocha.domain.member.MemberInfo;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping("/api/v2/member")
public class MemberController implements MemberApi {

	private final MemberFacade memberFacade;
	private final MemberDtoMapper memberDtoMapper;

	@Override
	@GetMapping("")
	public ResponseEntity<ResultDto<MemberDto.MyInfoResponse>> myInfo(
		@AuthenticationPrincipal UserPrincipal userPrincipal
	) {
		log.info("myInfo started memberId={}", userPrincipal.getId());

		Long memberId = userPrincipal.getId();

		MemberInfo.RetrieveMyInfo myInfo = memberFacade.retrieveMyInfo(memberId);

		MemberDto.MyInfoResponse response = memberDtoMapper.toResponse(myInfo);

		ResultDto<MemberDto.MyInfoResponse> result = ResultDto.res(
			MEMBER_INFO_RETRIEVE_SUCCESS.getStatusCode(),
			MEMBER_INFO_RETRIEVE_SUCCESS.getDescription(),
			response
		);

		log.info("currentMemberInfo finished ");

		return ResponseEntity
			.status(MEMBER_INFO_RETRIEVE_SUCCESS.getHttpStatus())
			.body(result);
	}

	// EXPLAIN : 상대방 정보 불러오기
	@Override
	@GetMapping("/{member_id}")
	public ResponseEntity<ResultDto<MemberDto.MemberInfoResponse>> memberInfo(
		@PathVariable("member_id") Long memberId
	) {
		MemberInfo.RetrieveMemberInfo memberInfo = memberFacade.retrieveMemberInfo(memberId);

		MemberDto.MemberInfoResponse response = memberDtoMapper.toResponse(memberInfo);

		ResultDto<MemberDto.MemberInfoResponse> result = ResultDto.res(
			MEMBER_INFO_RETRIEVE_SUCCESS.getStatusCode(),
			MEMBER_INFO_RETRIEVE_SUCCESS.getDescription(),
			response
		);

		return ResponseEntity
			.status(MEMBER_INFO_RETRIEVE_SUCCESS.getHttpStatus())
			.body(result);

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
		@RequestBody @Valid MemberDto.PasswordModifyRequest passwordModifyRequest
	) {
		log.info("passwordModify started memberId={}", userPrincipal.getId());

		Long memberId = userPrincipal.getId();

		MemberCommand.ModifyPassword modifyPasswordCommand = memberDtoMapper.toCommand(
			memberId,
			passwordModifyRequest
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
	public ResponseEntity<ResultDto<MemberDto.MyInfoModifyResponse>> myInfoModify(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@RequestBody @Valid MemberDto.MyInfoModifyRequest modifyRequest
	) {

		log.info("memberInfoModify started memberId={} , memberModifyRequest={}", userPrincipal.getId(),
			modifyRequest);

		Long memberId = userPrincipal.getId();

		MemberCommand.ModifyMyInfo modifyCommand = memberDtoMapper.toCommand(memberId, modifyRequest);

		MemberInfo.ModifyMyInfo modifyInfo = memberFacade.modifyMyInfo(modifyCommand);

		MemberDto.MyInfoModifyResponse response = memberDtoMapper.toResponse(modifyInfo);

		ResultDto<MemberDto.MyInfoModifyResponse> result = ResultDto.res(
			MEMBER_INFO_UPDATED.getStatusCode(),
			MEMBER_INFO_UPDATED.getDescription(),
			response
		);

		log.info("memberInfoModify finished");

		return ResponseEntity
			.status(MEMBER_INFO_UPDATED.getHttpStatus())
			.body(result);
	}

}
