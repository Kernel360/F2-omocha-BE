package org.auction.client.exception;

import static org.auction.client.common.code.AuctionErrorCode.*;

import org.auction.client.common.dto.ResultDto;
import org.auction.client.exception.auction.AuctionException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(AuctionException.class)
	public ResponseEntity<ResultDto<Object>> handleAuctionException(
		AuctionException e,
		HttpServletRequest request
	) {
		log.error("errorCode: {}, url: {}, message: {}",
			e.getAuctionErrorCode(), request.getRequestURI(), e.getDetailMessage(), e);

		ResultDto<Object> resultDto = ResultDto.res(
			e.getAuctionErrorCode().getStatusCode(),
			e.getAuctionErrorCode().getResultMsg()
		);
		return ResponseEntity
			.status(e.getAuctionErrorCode().getHttpStatus())
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
			UNSUPPORTED_MEDIA_TYPE.getResultMsg()
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
			INTERNAL_SERVER_ERROR.getResultMsg()
		);
		return ResponseEntity
			.status(INTERNAL_SERVER_ERROR.getHttpStatus())
			.body(resultDto);
	}
}