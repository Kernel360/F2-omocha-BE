package org.auction.client.member.interfaces;

import static org.auction.client.common.code.MemberCode.*;

import org.auction.client.common.dto.ResultDto;
import org.auction.client.jwt.application.JwtService;
import org.auction.client.member.application.MemberService;
import org.auction.client.member.interfaces.request.MemberCreateRequest;
import org.auction.client.member.interfaces.request.MemberLoginRequest;
import org.auction.domain.member.domain.entity.MemberEntity;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<ResultDto<String>> memberAdd(
		@RequestBody @Valid MemberCreateRequest memberCreateRequest
	) {
		log.info("Received MemberAddRequest: {}", memberCreateRequest);
		log.debug("Member register started");

		String response = memberService.addMember(memberCreateRequest);

		ResultDto<String> resultDto = ResultDto.res(
			MEMBER_CREATE_SUCCESS.getStatusCode(),
			MEMBER_CREATE_SUCCESS.getResultMsg(),
			response
		);

		return ResponseEntity
			.status(MEMBER_CREATE_SUCCESS.getHttpStatus())
			.body(resultDto);
	}

	@Override
	@PostMapping("/login")
	public ResponseEntity<ResultDto<Void>> memberLogin(
		HttpServletResponse response,
		@RequestBody @Valid MemberLoginRequest memberLoginRequest
	) {
		log.info("Received MemberLoginRequest: {}", memberLoginRequest);
		log.debug("Member login started");

		MemberEntity requestMember = memberService.findMemberByLoginId(memberLoginRequest.loginId());
		memberService.validateLogin(memberLoginRequest, requestMember);

		jwtService.generateAccessToken(response, requestMember);
		jwtService.generateRefreshToken(response, requestMember);

		ResultDto<Void> resultDto = ResultDto.res(
			MEMBER_LOGIN_SUCCESS.getStatusCode(),
			MEMBER_LOGIN_SUCCESS.getResultMsg(),
			null
		);

		return ResponseEntity
			.status(MEMBER_LOGIN_SUCCESS.getHttpStatus())
			.body(resultDto);
	}
}
