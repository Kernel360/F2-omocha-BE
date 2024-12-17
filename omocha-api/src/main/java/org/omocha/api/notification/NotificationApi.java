package org.omocha.api.notification;

import org.omocha.api.auth.jwt.UserPrincipal;
import org.omocha.api.common.response.ResultDto;
import org.omocha.api.notification.dto.NotificationDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "알림 API(NotificationController)", description = "알림 관련 API 입니다.")
public interface NotificationApi {

	@Operation(summary = "SSE 연결", description = "SSE 연결을 실행합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "SSE 연결 성공",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class)))
	})
	ResponseEntity<SseEmitter> connect(
		@Parameter(description = "사용자 객체 정보", required = true)
		UserPrincipal userPrincipal,
		@Parameter(description = "마지막 이벤트 ID", required = false)
		String lastEventId
	);

	@Operation(summary = "알림 읽음 처리", description = "알림을 읽음 상태로 변경합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "읽음 처리 성공",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class)))
	})
	ResponseEntity<ResultDto<Void>> read(
		@Parameter(description = "사용자 객체 정보", required = true)
		UserPrincipal userPrincipal,
		@Parameter(description = "읽음 처리를 할 알림 ID", required = true)
		Long notificationId
	);

	@Operation(summary = "전체 알림 읽음 처리", description = "모든 알림을 읽음 상태로 변경합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "읽음 처리 성공",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class))),
		@ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultDto.class)))
	})
	ResponseEntity<ResultDto<Void>> readAll(
		@Parameter(description = "사용자 객체 정보", required = true)
		UserPrincipal userPrincipal,
		@Parameter(description = "읽음 처리를 할 알림 ID List", required = true)
		NotificationDto.ReadAll readAllDto
	);
}
