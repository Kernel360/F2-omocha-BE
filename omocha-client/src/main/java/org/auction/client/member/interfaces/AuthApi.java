package org.auction.client.member.interfaces;

import org.auction.client.common.dto.ResultDto;
import org.auction.client.jwt.UserPrincipal;
import org.auction.client.member.interfaces.request.MemberCreateRequest;
import org.auction.client.member.interfaces.request.MemberDuplicateRequest;
import org.auction.client.member.interfaces.request.MemberLoginRequest;
import org.auction.client.member.interfaces.response.MemberDetailResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;

@Tag(name = "회원 API(AuthController)", description = "새로운 회원 생성, 로그인, 로그아웃 API 입니다.")
public interface AuthApi {

	// REFACTOR : exception 처리를 제대로 공부한 후 refactoring 해야함
	@Operation(summary = "회원 생성", description = "새로운 회원을 생성합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "회원을 성공적으로 생성하였습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "400", description = "이미 존재하는 회원입니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class)))
	})
	ResponseEntity<ResultDto<MemberDetailResponse>> memberAdd(
		@Parameter(description = "회원 생성 데이터", required = true)
		MemberCreateRequest memberCreateRequest
	);

	@Operation(summary = "회원 중복체크", description = "중복 회원이 있는지 확인합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "중복 회원이 존재하지 않습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "400", description = "중복 회원이 존재합니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class)))
	})
	ResponseEntity<ResultDto<Boolean>> checkEmailValidate(
		@Parameter(description = "중복 회원", required = true)
		MemberDuplicateRequest memberDuplicateRequest
	);

	@Operation(summary = "회원 로그인", description = "회원 로그인을 수행합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "성공적으로 로그인 하였습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "400", description = "비밀번호가 일치하지 않습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "404", description = "회원을 찾을 수 없습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class)))
	})
	ResponseEntity<ResultDto<MemberDetailResponse>> memberLogin(
		@Parameter(description = "요청 응답", required = true)
		HttpServletResponse response,

		@Parameter(description = "회원 로그인 데이터", required = true)
		MemberLoginRequest memberLoginRequest
	);

	@Operation(summary = "회원 로그아웃", description = "회원 로그아웃을 수행합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "성공적으로 로그아웃 하였습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "404", description = "회원을 찾을 수 없습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class)))
	})
	ResponseEntity<ResultDto<Void>> memberLogout(
		@Parameter(description = "요청 응답", required = true)
		HttpServletResponse response,

		@Parameter(description = "사용자 객체 정보", required = true)
		@AuthenticationPrincipal UserPrincipal userPrincipal
	);
}
