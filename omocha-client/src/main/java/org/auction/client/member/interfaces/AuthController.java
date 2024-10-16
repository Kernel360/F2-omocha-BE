package org.auction.client.member.interfaces;

import static org.auction.client.common.code.MemberCode.*;

import org.auction.client.common.dto.ResultDto;
import org.auction.client.jwt.UserPrincipal;
import org.auction.client.jwt.application.JwtService;
import org.auction.client.member.application.MemberService;
import org.auction.client.member.interfaces.request.MemberCreateRequest;
import org.auction.client.member.interfaces.request.MemberDuplicateRequest;
import org.auction.client.member.interfaces.request.MemberLoginRequest;
import org.auction.client.member.interfaces.response.MemberDetailResponse;
import org.auction.domain.member.domain.entity.MemberEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController implements AuthApi {

	private final MemberService memberService;
	private final JwtService jwtService;

	@Override
	@PostMapping("/register")
	public ResponseEntity<ResultDto<MemberDetailResponse>> memberAdd(
		@RequestBody @Valid MemberCreateRequest memberCreateRequest
	) {
		log.debug("Member register started");
		log.info("Received MemberAddRequest: {}", memberCreateRequest);

		MemberDetailResponse responseMember = memberService.addMember(memberCreateRequest);

		ResultDto<MemberDetailResponse> resultDto = ResultDto.res(
			MEMBER_CREATE_SUCCESS.getStatusCode(),
			MEMBER_CREATE_SUCCESS.getResultMsg(),
			responseMember
		);

		return ResponseEntity
			.status(MEMBER_CREATE_SUCCESS.getHttpStatus())
			.body(resultDto);
	}

	@Override
	@GetMapping("/validate-email")
	public ResponseEntity<ResultDto<Boolean>> checkEmailValidate(
		MemberDuplicateRequest memberDuplicateRequest
	) {
		log.debug("Email Duplication Check started");
		log.info("Received memberDuplicateRequest: {}", memberDuplicateRequest);

		boolean duplicate = memberService.isEmailDuplicate(memberDuplicateRequest);

		ResultDto<Boolean> resultDto = ResultDto.res(
			MEMBER_CREATE_SUCCESS.getStatusCode(),
			MEMBER_CREATE_SUCCESS.getResultMsg(),
			duplicate
		);

		return ResponseEntity
			.status(MEMBER_CREATE_SUCCESS.getHttpStatus())
			.body(resultDto);
	}

	@Override
	@PostMapping("/login")
	public ResponseEntity<ResultDto<MemberDetailResponse>> memberLogin(
		HttpServletResponse response,
		@RequestBody @Valid MemberLoginRequest memberLoginRequest
	) {
		log.debug("Member login started");
		log.info("Received MemberLoginRequest: {}", memberLoginRequest);

		MemberEntity member = memberService.findMember(memberLoginRequest);

		jwtService.generateAccessToken(response, member);
		jwtService.generateRefreshToken(response, member);

		ResultDto<MemberDetailResponse> resultDto = ResultDto.res(
			MEMBER_LOGIN_SUCCESS.getStatusCode(),
			MEMBER_LOGIN_SUCCESS.getResultMsg(),
			MemberDetailResponse.toDto(member)
		);

		return ResponseEntity
			.status(MEMBER_LOGIN_SUCCESS.getHttpStatus())
			.body(resultDto);
	}

	@Override
	@PostMapping("/logout")
	public ResponseEntity<ResultDto<Void>> memberLogout(
		HttpServletResponse response,
		@AuthenticationPrincipal UserPrincipal userPrincipal
	) {
		log.debug("Member logout started");

		jwtService.logout(userPrincipal.getMemberEntity(), response);

		ResultDto<Void> resultDto = ResultDto.res(
			MEMBER_LOGOUT_SUCCESS.getStatusCode(),
			MEMBER_LOGOUT_SUCCESS.getResultMsg(),
			null
		);

		return ResponseEntity
			.status(MEMBER_LOGOUT_SUCCESS.getHttpStatus())
			.body(resultDto);
	}

}
