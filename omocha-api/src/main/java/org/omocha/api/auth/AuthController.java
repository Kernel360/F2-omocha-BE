package org.omocha.api.auth;

import org.omocha.api.auth.dto.AuthDto;
import org.omocha.api.auth.dto.AuthDtoMapper;
import org.omocha.api.auth.jwt.JwtProvider;
import org.omocha.api.auth.jwt.RefreshTokenManager;
import org.omocha.api.auth.jwt.UserPrincipal;
import org.omocha.api.common.response.ResultDto;
import org.omocha.api.common.util.PasswordManager;
import org.omocha.domain.common.code.SuccessCode;
import org.omocha.domain.member.MemberCommand;
import org.omocha.domain.member.vo.Email;
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
public class AuthController implements AuthApi {

	private final AuthFacade authFacade;
	private final AuthDtoMapper authDtoMapper;
	private final JwtProvider jwtProvider;
	private final PasswordManager passwordManager;

	@Override
	@PostMapping("/register")
	public ResponseEntity<ResultDto<Void>> memberAdd(
		@RequestBody @Valid AuthDto.MemberAddRequest memberAddRequest
	) {
		// log.debug("Member register started");
		// log.info("Received MemberAddRequest: {}", memberCreateRequest);

		MemberCommand.AddMember addMemberCommand = authDtoMapper.toCommand(
			memberAddRequest.email(),
			passwordManager.encrypt(memberAddRequest.password())
		);

		authFacade.addMember(addMemberCommand);

		ResultDto<Void> resultDto = ResultDto.res(
			SuccessCode.MEMBER_CREATE_SUCCESS.getStatusCode(),
			SuccessCode.MEMBER_CREATE_SUCCESS.getDescription()

		);

		return ResponseEntity
			.status(SuccessCode.MEMBER_CREATE_SUCCESS.getHttpStatus())
			.body(resultDto);
	}

	// TODO : security 추가 이후에 작업 필요
	@Override
	@GetMapping("/validate-email")
	public ResponseEntity<ResultDto<Boolean>> emailValidateCheck(
		@RequestParam String email
	) {

		boolean duplicate = authFacade.isEmailDuplicate(new Email(email));

		ResultDto<Boolean> resultDto = ResultDto.res(
			SuccessCode.VALIDATE_EMAIL_SUCCESS.getStatusCode(),
			SuccessCode.VALIDATE_EMAIL_SUCCESS.getDescription(),
			duplicate
		);

		return ResponseEntity
			.status(SuccessCode.VALIDATE_EMAIL_SUCCESS.getHttpStatus())
			.body(resultDto);
	}

	@Override
	@PostMapping("/login")
	public ResponseEntity<ResultDto<Void>> memberLogin(
		@RequestBody @Valid AuthDto.MemberLoginRequest memberLoginRequest,
		HttpServletResponse response
	) {
		log.debug("Member login started");
		log.info("Received MemberLoginRequest: {}", memberLoginRequest);

		MemberCommand.MemberLogin memberLogin = authDtoMapper.toCommand(memberLoginRequest);

		authFacade.memberLogin(memberLogin, response);

		ResultDto<Void> resultDto = ResultDto.res(
			SuccessCode.MEMBER_LOGIN_SUCCESS.getStatusCode(),
			SuccessCode.MEMBER_LOGIN_SUCCESS.getDescription()
		);

		return ResponseEntity
			.status(SuccessCode.MEMBER_LOGIN_SUCCESS.getHttpStatus())
			.body(resultDto);
	}

	@Override
	@PostMapping("/logout")
	public ResponseEntity<ResultDto<Void>> memberLogout(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		HttpServletResponse response
	) {
		log.debug("Member logout started");

		RefreshTokenManager.removeUserRefreshToken(userPrincipal.getMember().getMemberId());
		jwtProvider.logout(response);

		ResultDto<Void> resultDto = ResultDto.res(
			SuccessCode.MEMBER_LOGOUT_SUCCESS.getStatusCode(),
			SuccessCode.MEMBER_LOGOUT_SUCCESS.getDescription()
		);

		return ResponseEntity
			.status(SuccessCode.MEMBER_LOGOUT_SUCCESS.getHttpStatus())
			.body(resultDto);
	}
}
