package org.auction.client.exception;

import org.auction.client.common.dto.ResultDto;
import org.auction.client.exception.auction.AuctionCreationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(AuctionCreationException.class)
	public ResponseEntity<ResultDto<String>> handleAuctionCreationException(
		AuctionCreationException ex
	) {
		ResultDto<String> resultDto = ResultDto.res(
			HttpStatus.BAD_REQUEST, ex.getMessage());
		return new ResponseEntity<>(resultDto, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ResultDto<String>> handleEntityNotFoundException(
		EntityNotFoundException ex
	) {
		ResultDto<String> resultDto = ResultDto.res(
			HttpStatus.NOT_FOUND, ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultDto);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResultDto<Object>> handleGeneralException(Exception ex) {
		log.error("Exception occurred: ", ex); // 에러 로그 출력
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(new ResultDto<>(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다.", null));
	}

	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ResponseEntity<ResultDto<Object>> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex) {
		log.error("Unsupported content type: ", ex);
		return ResponseEntity
			.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
			.body(new ResultDto<>(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "지원하지 않는 Content-Type 입니다.", null));
	}
}
