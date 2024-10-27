package org.omocha.api.interfaces;

import static org.omocha.domain.exception.code.MemberCode.*;

import org.omocha.api.application.MemberFacade;
import org.omocha.api.common.response.ResultDto;
import org.omocha.api.interfaces.dto.MemberDto;
import org.omocha.api.interfaces.mapper.MemberDtoMapper;
import org.omocha.domain.member.MemberCommand;
import org.omocha.domain.member.MemberInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

	private final MemberFacade memberFacade;
	private final MemberDtoMapper memberDtoMapper;
	// private final JwtService jwtService;

	@PostMapping("/register")
	public ResponseEntity<ResultDto<MemberDto.MemberDetailResponse>> memberAdd(
		@RequestBody @Valid MemberDto.MemberCreateRequest memberCreateRequest
	) {
		// log.debug("Member register started");
		// log.info("Received MemberAddRequest: {}", memberCreateRequest);

		MemberCommand.MemberCreateCommand memberCreateCommand = memberDtoMapper.of(memberCreateRequest);

		MemberInfo.MemberDetailInfo memberDetailInfo = memberFacade.addMember(memberCreateCommand);

		MemberDto.MemberDetailResponse memberDetailResponse = memberDtoMapper.of(memberDetailInfo);

		ResultDto<MemberDto.MemberDetailResponse> resultDto = ResultDto.res(
			MEMBER_CREATE_SUCCESS.getStatusCode(),
			MEMBER_CREATE_SUCCESS.getResultMsg(),
			memberDetailResponse

		);

		return ResponseEntity
			.status(MEMBER_CREATE_SUCCESS.getHttpStatus())
			.body(resultDto);
	}

	// TODO : security 추가 이후에 작업 필요
	// @GetMapping("/validate-email")
	// public ResponseEntity<ResultDto<Boolean>> checkEmailValidate(
	// 	@RequestParam String email
	// ) {
	// 	// log.debug("Email Duplication Check started");
	// 	// log.info("Received memberDuplicateRequest: {}", email);
	//
	// 	MemberCommand.MemberDuplicateCommand memberDuplicateCommand = memberDtoMapper.of(email);
	//
	// 	boolean duplicate = memberFacade.isEmailDuplicate(email);
	//
	// 	ResultDto<Boolean> resultDto = ResultDto.res(
	// 		VALIDATE_EMAIL_SUCCESS.getStatusCode(),
	// 		VALIDATE_EMAIL_SUCCESS.getResultMsg(),
	// 		duplicate
	// 	);
	//
	// 	return ResponseEntity
	// 		.status(VALIDATE_EMAIL_SUCCESS.getHttpStatus())
	// 		.body(resultDto);
	// }

	// TODO : security 추가 이후에 작업 필요
	// @Override
	// @PostMapping("/login")
	// public ResponseEntity<ResultDto<Void>> memberLogin(
	// 	HttpServletResponse response,
	// 	@RequestBody @Valid MemberDto.MemberLoginRequest memberLoginRequest
	// ) {
	// 	log.debug("Member login started");
	// 	log.info("Received MemberLoginRequest: {}", memberLoginRequest);
	//
	// 	Member member = memberFacade.findMember(memberLoginRequest);
	//
	// 	jwtService.generateAccessToken(response, member);
	// 	jwtService.generateRefreshToken(response, member);
	//
	// 	ResultDto<Void> resultDto = ResultDto.res(
	// 		MEMBER_LOGIN_SUCCESS.getStatusCode(),
	// 		MEMBER_LOGIN_SUCCESS.getResultMsg()
	// 	);
	//
	// 	return ResponseEntity
	// 		.status(MEMBER_LOGIN_SUCCESS.getHttpStatus())
	// 		.body(resultDto);
	// }

	// TODO : security 추가 이후에 작업 필요
	// @Override
	// @PostMapping("/logout")
	// public ResponseEntity<ResultDto<Void>> memberLogout(
	// 	HttpServletResponse response,
	// 	@AuthenticationPrincipal UserPrincipal userPrincipal
	// ) {
	// 	log.debug("Member logout started");
	//
	// 	jwtService.logout(userPrincipal.getMemberEntity(), response);
	//
	// 	ResultDto<Void> resultDto = ResultDto.res(
	// 		MEMBER_LOGOUT_SUCCESS.getStatusCode(),
	// 		MEMBER_LOGOUT_SUCCESS.getResultMsg()
	// 	);
	//
	// 	return ResponseEntity
	// 		.status(MEMBER_LOGOUT_SUCCESS.getHttpStatus())
	// 		.body(resultDto);
	// }

}
