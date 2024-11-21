package org.omocha.api.common.handler;

import static org.omocha.domain.common.code.ErrorCode.*;

import org.omocha.api.common.response.ResultDto;
import org.omocha.domain.common.exception.OmochaException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	// TODO: 추후 controller 내부로 리팩토링 필요

	@ExceptionHandler(OmochaException.class)
	public ResponseEntity<ResultDto<Object>> handleOmochaException(
		OmochaException e,
		HttpServletRequest request
	) {
		// TODO: 로그에 치환문자{} 를 3개 이상 사용할 경우 Object[] 를 생성하는 비용이 발생
		log.error("Request URI: {}, Method: {}, Params: {}",
			request.getRequestURI(),
			request.getMethod(),
			request.getQueryString(),
			e);

		ResultDto<Object> resultDto = ResultDto.res(
			e.getErrorCode().getStatusCode(),
			e.getMessage()
		);
		return ResponseEntity
			.status(e.getErrorCode().getHttpStatus())
			.body(resultDto);
	}

	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ResponseEntity<ResultDto<Object>> handleHttpMediaTypeNotSupported(
		HttpMediaTypeNotSupportedException e,
		HttpServletRequest request
	) {
		log.error("errorCode: {}, url: {}, message: {}",
			UNSUPPORTED_MEDIA_TYPE, request.getRequestURI(), e.getMessage(), e);

		ResultDto<Object> resultDto = ResultDto.res(
			UNSUPPORTED_MEDIA_TYPE.getStatusCode(),
			UNSUPPORTED_MEDIA_TYPE.getDescription()
		);
		return ResponseEntity
			.status(UNSUPPORTED_MEDIA_TYPE.getHttpStatus())
			.body(resultDto);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResultDto<Object>> handleGeneralException(
		Exception e,
		HttpServletRequest request
	) {
		log.error("url: {}, message: {}", request.getRequestURL(), e.getMessage(), e);
		ResultDto<Object> resultDto = ResultDto.res(
			INTERNAL_SERVER_ERROR.getStatusCode(),
			INTERNAL_SERVER_ERROR.getDescription()
		);
		return ResponseEntity
			.status(INTERNAL_SERVER_ERROR.getHttpStatus())
			.body(resultDto);
	}
}