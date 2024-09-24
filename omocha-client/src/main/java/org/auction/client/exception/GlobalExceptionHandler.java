package org.auction.client.exception;

import org.auction.client.common.dto.ResultDto;
import org.auction.client.exception.auction.AuctionCreationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(AuctionCreationException.class)
	public ResponseEntity<ResultDto<String>> handleAuctionCreationException(
		AuctionCreationException ex
	) {
		ResultDto<String> resultDto = ResultDto.res(
			HttpStatus.BAD_REQUEST, ex.getMessage(), null);
		return new ResponseEntity<>(resultDto, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ResultDto<String>> handleEntityNotFoundException(
		EntityNotFoundException ex
	) {
		ResultDto<String> resultDto = ResultDto.res(
			HttpStatus.NOT_FOUND, ex.getMessage(), null);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultDto);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResultDto<String>> handleGeneralException(
		Exception ex
	) {
		ResultDto<String> resultDto = ResultDto.res(
			HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다.", null);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultDto);
	}

}
