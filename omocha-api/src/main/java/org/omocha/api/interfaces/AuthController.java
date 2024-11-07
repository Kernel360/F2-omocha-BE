package org.omocha.api.interfaces;

import static org.omocha.domain.exception.code.MemberCode.*;

import org.omocha.api.application.MemberFacade;
import org.omocha.api.common.auth.jwt.JwtProvider;
import org.omocha.api.common.auth.jwt.UserPrincipal;
import org.omocha.api.common.response.ResultDto;
import org.omocha.api.common.util.PasswordManager;
import org.omocha.api.interfaces.dto.MemberDto;
import org.omocha.api.interfaces.mapper.MemberDtoMapper;
import org.omocha.domain.member.MemberCommand;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/auth")
public class AuthController {

	private final MemberFacade memberFacade;
	private final MemberDtoMapper memberDtoMapper;
	private final JwtProvider jwtProvider;
	private final PasswordManager passwordManager;

	@PostMapping("/register")
	public ResponseEntity<ResultDto<Void>> memberAdd(
		@RequestBody @Valid MemberDto.MemberAddRequest memberAddRequest
	) {
		// log.debug("Member register started");
		// log.info("Received MemberAddRequest: {}", memberCreateRequest);

		MemberCommand.AddMember addMemberCommand = memberDtoMapper.toCommand(memberAddRequest.email(),
			passwordManager.encrypt(memberAddRequest.password()));

		memberFacade.addMember(addMemberCommand);

		ResultDto<Void> resultDto = ResultDto.res(
			MEMBER_CREATE_SUCCESS.getStatusCode(),
			MEMBER_CREATE_SUCCESS.getResultMsg()

		);

		return ResponseEntity
			.status(MEMBER_CREATE_SUCCESS.getHttpStatus())
			.body(resultDto);
	}

	// TODO : security 추가 이후에 작업 필요
	@GetMapping("/validate-email")
	public ResponseEntity<ResultDto<Boolean>> emailValidateCheck(
		@RequestParam String email
	) {

		boolean duplicate = memberFacade.isEmailDuplicate(email);

		ResultDto<Boolean> resultDto = ResultDto.res(
			VALIDATE_EMAIL_SUCCESS.getStatusCode(),
			VALIDATE_EMAIL_SUCCESS.getResultMsg(),
			duplicate
		);

		return ResponseEntity
			.status(VALIDATE_EMAIL_SUCCESS.getHttpStatus())
			.body(resultDto);
	}

	@PostMapping("/login")
	public ResponseEntity<ResultDto<Void>> memberLogin(
		@RequestBody @Valid MemberDto.MemberLoginRequest memberLoginRequest,
		HttpServletResponse response
	) {
		log.debug("Member login started");
		log.info("Received MemberLoginRequest: {}", memberLoginRequest);

		MemberCommand.MemberLogin memberLogin = memberDtoMapper.toCommand(memberLoginRequest);

		memberFacade.memberLogin(memberLogin, response);

		ResultDto<Void> resultDto = ResultDto.res(
			MEMBER_LOGIN_SUCCESS.getStatusCode(),
			MEMBER_LOGIN_SUCCESS.getResultMsg()
		);

		return ResponseEntity
			.status(MEMBER_LOGIN_SUCCESS.getHttpStatus())
			.body(resultDto);
	}

	@PostMapping("/logout")
	public ResponseEntity<ResultDto<Void>> memberLogout(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		HttpServletResponse response
	) {
		log.debug("Member logout started");

		jwtProvider.logout(userPrincipal.getMember().getMemberId(), response);

		ResultDto<Void> resultDto = ResultDto.res(
			MEMBER_LOGOUT_SUCCESS.getStatusCode(),
			MEMBER_LOGOUT_SUCCESS.getResultMsg()
		);

		return ResponseEntity
			.status(MEMBER_LOGOUT_SUCCESS.getHttpStatus())
			.body(resultDto);
	}
}
