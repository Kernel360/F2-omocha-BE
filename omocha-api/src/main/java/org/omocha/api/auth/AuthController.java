package org.omocha.api.auth;

import org.omocha.api.auth.dto.AuthDto;
import org.omocha.api.auth.dto.AuthDtoMapper;
import org.omocha.api.common.response.ResultDto;
import org.omocha.domain.common.code.SuccessCode;
import org.omocha.domain.member.MemberCommand;
import org.omocha.domain.member.vo.Email;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

	@Override
	@PostMapping("/register")
	public ResponseEntity<ResultDto<Void>> memberAdd(
		@RequestBody @Valid AuthDto.MemberAddRequest memberAddRequest
	) {
		log.debug("Member register started");
		log.info("Received MemberAddRequest: {}", memberAddRequest);

		MemberCommand.AddMember addMemberCommand = authDtoMapper.toCommand(
			memberAddRequest.email(),
			memberAddRequest.password()
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

	@Override
	@GetMapping("/validate-email")
	public ResponseEntity<ResultDto<Boolean>> emailValidateCheck(
		@RequestParam String email
	) {
		log.debug("Email Validate Check started");
		log.info("Received email: {}", email);

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
	public ResponseEntity<ResultDto<AuthDto.JwtResponse>> memberLogin(
		@RequestBody @Valid AuthDto.MemberLoginRequest memberLoginRequest
	) {
		log.debug("Member login started");
		log.info("Received MemberLoginRequest: {}", memberLoginRequest);

		MemberCommand.LoginMember loginMember = authDtoMapper.toCommand(memberLoginRequest);

		AuthDto.JwtResponse result = authFacade.loginMember(loginMember);

		ResultDto<AuthDto.JwtResponse> resultDto = ResultDto.res(
			SuccessCode.MEMBER_LOGIN_SUCCESS.getStatusCode(),
			SuccessCode.MEMBER_LOGIN_SUCCESS.getDescription(),
			result
		);

		return ResponseEntity
			.status(SuccessCode.MEMBER_LOGIN_SUCCESS.getHttpStatus())
			.body(resultDto);
	}

	@Override
	@PostMapping("/token-reissue")
	public ResponseEntity<ResultDto<AuthDto.JwtResponse>> tokenReissue(
		@RequestBody @Valid AuthDto.TokenReissueRequest tokenReissueRequest
	) {
		log.debug("Token Reissue started");
		log.info("Received tokenReissueRequest: {}", tokenReissueRequest);

		MemberCommand.ReissueToken reissueToken = authDtoMapper.toCommand(tokenReissueRequest);

		AuthDto.JwtResponse result = authFacade.reissueToken(reissueToken);

		ResultDto<AuthDto.JwtResponse> resultDto = ResultDto.res(
			SuccessCode.TOKEN_REISSUE_SUCCESS.getStatusCode(),
			SuccessCode.TOKEN_REISSUE_SUCCESS.getDescription(),
			result
		);

		return ResponseEntity
			.status(SuccessCode.TOKEN_REISSUE_SUCCESS.getHttpStatus())
			.body(resultDto);
	}
}
