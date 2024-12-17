package org.omocha.api.common.handler;

import static org.omocha.domain.common.code.ErrorCode.*;

import java.util.List;
import java.util.stream.Collectors;

import org.omocha.api.common.response.ResultDto;
import org.omocha.domain.common.exception.OmochaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(OmochaException.class)
	public ResponseEntity<ResultDto<Object>> handleOmochaException(
		OmochaException e,
		HttpServletRequest request
	) {
		warnLogging(e, request);

		ResultDto<Object> resultDto = ResultDto.res(
			e.getErrorCode().getStatusCode(),
			e.getErrorCode().getDescription()
		);

		return ResponseEntity
			.status(e.getErrorCode().getHttpStatus())
			.body(resultDto);
	}

	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<ResultDto<Object>> handleNoResourceFoundException(
		NoResourceFoundException e,
		HttpServletRequest request
	) {
		warnLogging(e, request);

		ResultDto<Object> resultDto = ResultDto.res(
			URL_NOT_FOUND.getStatusCode(),
			URL_NOT_FOUND.getDescription()
		);

		return ResponseEntity
			.status(URL_NOT_FOUND.getHttpStatus())
			.body(resultDto);
	}

	// TODO : Global Handler에서 exception 처리 안하기
	@ExceptionHandler(MissingServletRequestPartException.class)
	public ResponseEntity<ResultDto<Object>> handleMissingServletRequestPart(
		MissingServletRequestPartException e,
		HttpServletRequest request
	) {
		warnLogging(e, request);

		ResultDto<Object> resultDto = ResultDto.res(
			REQUEST_PART_NOT_FOUND.getStatusCode(),
			REQUEST_PART_NOT_FOUND.getDescription()
		);

		return ResponseEntity
			.status(REQUEST_PART_NOT_FOUND.getHttpStatus())
			.body(resultDto);
	}

	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ResponseEntity<ResultDto<Object>> handleHttpMediaTypeNotSupported(
		HttpMediaTypeNotSupportedException e,
		HttpServletRequest request
	) {
		warnLogging(e, request);

		ResultDto<Object> resultDto = ResultDto.res(
			UNSUPPORTED_MEDIA_TYPE.getStatusCode(),
			UNSUPPORTED_MEDIA_TYPE.getDescription()
		);

		return ResponseEntity
			.status(UNSUPPORTED_MEDIA_TYPE.getHttpStatus())
			.body(resultDto);
	}

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<ResultDto<Object>> handleMaxUploadSizeException(
		MaxUploadSizeExceededException e,
		HttpServletRequest request
	) {
		warnLogging(e, request);

		ResultDto<Object> resultDto = ResultDto.res(
			MAX_UPLOAD_SIZE_FAIL.getStatusCode(),
			MAX_UPLOAD_SIZE_FAIL.getDescription()
		);
		return ResponseEntity
			.status(MAX_UPLOAD_SIZE_FAIL.getHttpStatus())
			.body(resultDto);
	}

	// Request Json 파싱 에러
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ResultDto<Object>> handleHttpMessageNotReadable(
		HttpMessageNotReadableException e,
		HttpServletRequest request
	) {
		warnLogging(e, request);

		if (e.getCause() instanceof MismatchedInputException mismatchedInputException) {

			ResultDto<Object> fieldErrorResult = ResultDto.res(
				BAD_REQUEST_INVALID_FIELD.getStatusCode(),
				mismatchedInputException.getPath().get(0).getFieldName() + " 필드의 값이 잘못되었습니다."
			);

			return ResponseEntity
				.status(BAD_REQUEST_INVALID_FIELD.getHttpStatus())
				.body(fieldErrorResult);
		}

		ResultDto<Object> generalErrorResult = ResultDto.res(
			BAD_REQUEST_INVALID_FIELD.getStatusCode(),
			"확인할 수 없는 형태의 데이터가 들어왔습니다"
		);

		return ResponseEntity
			.status(BAD_REQUEST_INVALID_FIELD.getHttpStatus())
			.body(generalErrorResult);
	}

	// @Valid NotNull 예외처리
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ResultDto<Object>> handlerMethodArgumentNotValid(
		MethodArgumentNotValidException e,
		HttpServletRequest request
	) {
		warnLogging(e, request);

		List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();

		String fieldNames = fieldErrors.stream()
			.map(FieldError::getField)
			.collect(Collectors.joining(", "));

		ResultDto<Object> resultDto = ResultDto.res(
			NOT_NULL_FIELD.getStatusCode(),
			"[" + fieldNames + "], " + NOT_NULL_FIELD.getDescription()
		);

		return ResponseEntity
			.status(NOT_NULL_FIELD.getHttpStatus())
			.body(resultDto);
	}

	private void warnLogging(Exception e, HttpServletRequest request) {
		log.warn("Client IP: {}, Request URI: {}, Method: {}, Params: {}",
			request.getRemoteAddr(),
			request.getRequestURI(),
			request.getMethod(),
			request.getQueryString()
		);

		log.warn("Exception: {}, Message: {}",
			e.getClass().getName(),
			e.getMessage(),
			e
		);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResultDto<Object>> handleGeneralException(
		Exception e,
		HttpServletRequest request
	) {
		log.error("Client IP: {}, User-Agent: {}",
			request.getRemoteAddr(),
			request.getHeader("User-Agent")
		);

		log.error("Request URI: {}, Method: {}, Params: {}",
			request.getRequestURI(),
			request.getMethod(),
			request.getQueryString()
		);

		log.error("Message: {}",
			e.getMessage(),
			e
		);

		ResultDto<Object> resultDto = ResultDto.res(
			INTERNAL_SERVER_ERROR.getStatusCode(),
			INTERNAL_SERVER_ERROR.getDescription()
		);

		return ResponseEntity
			.status(INTERNAL_SERVER_ERROR.getHttpStatus())
			.body(resultDto);
	}

	// TODO: SSE Timeout 임시
	@ExceptionHandler(AsyncRequestTimeoutException.class)
	public void handleSSETimeoutException(HttpServletResponse response) {
		response.setStatus(HttpStatus.REQUEST_TIMEOUT.value());
	}
}