package org.omocha.api.common.handler;

import static org.omocha.domain.common.code.ErrorCode.*;

import org.omocha.api.common.response.ResultDto;
import org.omocha.domain.auction.exception.AuctionException;
import org.omocha.domain.bid.exception.BidException;
import org.omocha.domain.chat.exception.ChatException;
import org.omocha.domain.image.exception.ImageException;
import org.omocha.domain.member.exception.MemberException;
import org.omocha.domain.member.exception.jwt.JwtTokenException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	// TODO: 추후 controller 내부로 리팩토링 필요
	// TODO: ErrorCode로 통합하면서 handleGeneralException과 같이 resultMsg => description으로 변경 필요

	@ExceptionHandler(AuctionException.class)
	public ResponseEntity<ResultDto<Object>> handleAuctionException(
		AuctionException e,
		HttpServletRequest request
	) {
		// TODO: 로그에 치환문자{} 를 3개 이상 사용할 경우 Object[] 를 생성하는 비용이 발생
		log.error("errorCode: {}, url: {}, message: {}",
			e.getErrorCode(), request.getRequestURI(), e.getMessage(), e);

		ResultDto<Object> resultDto = ResultDto.res(
			e.getErrorCode().getStatusCode(),
			e.getMessage()
		);
		return ResponseEntity
			.status(e.getErrorCode().getHttpStatus())
			.body(resultDto);
	}

	@ExceptionHandler(BidException.class)
	public ResponseEntity<ResultDto<Object>> handleBidException(
		BidException e,
		HttpServletRequest request
	) {
		log.error("errorCode: {}, url: {}, message: {}",
			e.getErrorCode(), request.getRequestURI(), e.getMessage(), e);

		ResultDto<Object> resultDto = ResultDto.res(
			e.getErrorCode().getStatusCode(),
			e.getMessage()
		);
		return ResponseEntity
			.status(e.getErrorCode().getHttpStatus())
			.body(resultDto);
	}

	@ExceptionHandler(ImageException.class)
	public ResponseEntity<ResultDto<Object>> handleImageException(
		ImageException e,
		HttpServletRequest request
	) {
		log.error("errorCode: {}, url: {}, message: {}",
			e.getErrorCode(), request.getRequestURI(), e.getMessage(), e);

		ResultDto<Object> resultDto = ResultDto.res(
			e.getErrorCode().getStatusCode(),
			e.getMessage()
		);
		return ResponseEntity
			.status(e.getErrorCode().getHttpStatus())
			.body(resultDto);
	}

	@ExceptionHandler(JwtTokenException.class)
	public ResponseEntity<ResultDto<Object>> handleJWTException(
		JwtTokenException e,
		HttpServletRequest request
	) {
		log.error("errorCode: {}, url: {}, message: {}",
			e.getErrorCode(), request.getRequestURI(), e.getMessage(), e);

		ResultDto<Object> resultDto = ResultDto.res(
			e.getErrorCode().getStatusCode(),
			e.getMessage()
		);
		return ResponseEntity
			.status(e.getErrorCode().getHttpStatus())
			.body(resultDto);
	}

	@ExceptionHandler(MemberException.class)
	public ResponseEntity<ResultDto<Object>> handleMemberException(
		MemberException e,
		HttpServletRequest request
	) {
		log.error("errorCode: {}, url: {}, message: {}",
			e.getMemberCode(), request.getRequestURI(), e.getMessage(), e);

		ResultDto<Object> resultDto = ResultDto.res(
			e.getMemberCode().getStatusCode(),
			e.getMessage()
		);
		return ResponseEntity
			.status(e.getMemberCode().getHttpStatus())
			.body(resultDto);
	}

	@ExceptionHandler(ChatException.class)
	public ResponseEntity<ResultDto<Object>> handleMemberException(
		ChatException e,
		HttpServletRequest request
	) {
		log.error("errorCode: {}, url: {}, message: {}",
			e.getErrorCode(), request.getRequestURI(), e.getMessage(), e);

		ResultDto<Object> resultDto = ResultDto.res(
			e.getErrorCode().getStatusCode(),
			e.getMessage()
		);
		return ResponseEntity
			.status(e.getErrorCode().getHttpStatus())
			.body(resultDto);
	}

	// TODO : Global Handler에서 exception 처리 안하기
	@ExceptionHandler(MissingServletRequestPartException.class)
	public ResponseEntity<ResultDto<Object>> missingServletRequestPartException(
		MissingServletRequestPartException e,
		HttpServletRequest request
	) {
		log.error("errorCode: {}, url: {}, message: {}",
			REQUEST_PART_NOT_FOUND, request.getRequestURI(), e.getMessage(), e);

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