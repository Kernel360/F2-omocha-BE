package org.omocha.api.auth;

import org.omocha.api.auth.dto.AuthDto;
import org.omocha.api.common.response.ResultDto;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "인증 API(AuthController)", description = "새로운 회원 생성, 로그인, 로그아웃 API 입니다.")
public interface AuthApi {

	@Operation(summary = "회원 생성", description = "새로운 회원을 생성합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "회원을 성공적으로 생성하였습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "400", description = "이미 존재하는 회원입니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class)))
	})
	ResponseEntity<ResultDto<Void>> memberAdd(
		@RequestBody(description = "회원가입 정보", required = true)
		AuthDto.MemberAddRequest memberAddRequest
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
	ResponseEntity<ResultDto<Boolean>> emailValidateCheck(
		@Parameter(description = "중복 체크할 email", required = true)
		String email
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
	ResponseEntity<ResultDto<AuthDto.JwtResponse>> memberLogin(
		@RequestBody(description = "사용자 로그인 정보", required = true)
		AuthDto.MemberLoginRequest memberLoginRequest
	);

	@Operation(summary = "토큰 재발급", description = "JWT를 재발급 합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "토큰을 성공적으로 재발급 하였습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class)))
	})
	ResponseEntity<ResultDto<AuthDto.JwtResponse>> tokenReissue(
		@RequestBody(description = "RefreshToken", required = true)
		AuthDto.TokenReissueRequest tokenReissueRequest
	);
}
