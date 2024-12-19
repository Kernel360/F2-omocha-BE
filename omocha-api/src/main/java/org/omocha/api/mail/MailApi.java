package org.omocha.api.mail;

import org.omocha.api.common.response.ResultDto;
import org.omocha.api.mail.dto.MailDto;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "메일 API(MailController)", description = "인증 메일 발송, 확인 API 입니다.")
public abstract class MailApi {

	@Operation(summary = "메일 발송", description = "메일을 발송합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "메일이 성공적으로 발송되었습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "400", description = "메일 전송에 실패했습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "400", description = "이미 존재하는 회원입니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "400", description = "잠시 후 다시 시도 바랍니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
	})
	public abstract ResponseEntity<ResultDto<Void>> mailSend(
		MailDto.MailSendRequest mailSendRequest
	);

	@Operation(summary = "메일 코드 확인", description = "인증 코드를 확인합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "메일 인증에 성공하였습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "404", description = "키 값을 찾을 수 없습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),

	})
	public abstract ResponseEntity<ResultDto<Boolean>> mailCodeVerification(
		MailDto.MailCodeVerificationRequest codeVerificationRequest
	);
}
