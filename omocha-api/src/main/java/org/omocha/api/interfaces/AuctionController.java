package org.omocha.api.interfaces;

import java.util.List;

import org.omocha.api.application.AuctionFacade;
import org.omocha.api.common.response.ResultDto;
import org.omocha.api.interfaces.dto.AuctionDto;
import org.omocha.api.interfaces.mapper.AuctionDtoMapper;
import org.omocha.domain.exception.code.AuctionCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/auction")
public class AuctionController {

	private final AuctionFacade auctionFacade;
	private final AuctionDtoMapper auctionDtoMapper;
	// private final PageSort pageSort;

	@PostMapping(
		consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<ResultDto> auctionSave(
		@RequestPart("auctionRequest") AuctionDto.CreateAuctionRequest auctionRequest,
		@RequestPart(value = "images", required = true) List<MultipartFile> images
	) {
		var auctionCommand = auctionDtoMapper.of(auctionRequest, images);
		var auctionId = auctionFacade.addAuction(auctionCommand);
		var response = auctionDtoMapper.of(auctionId);

		var result = ResultDto.success(response, "OK");

		return ResponseEntity
			.status(AuctionCode.AUCTION_CREATE_SUCCESS.getHttpStatus())
			.body(result);
	}

	/*@GetMapping("/basic-list")
	public ResponseEntity<ResultDto> auctionList(
		AuctionDto.AuctionSearchCondition condition,
		@RequestParam(value = "auctionStatus", required = false) AuctionStatus auctionStatus,
		@RequestParam(value = "sort", defaultValue = "createdAt") String sort,
		@RequestParam(value = "direction", defaultValue = "DESC") String direction,
		@PageableDefault(page = 0, size = 10)
		Pageable pageable
	) {
		Pageable sortPage = pageSort.sortPage(pageable, sort, direction);
		var auctionCommand = auctionDtoMapper.of(condition, auctionStatus);

		var searchResult = auctionFacade.searchAuction(auctionCommand, sortPage);

		var response = auctionDtoMapper.of(searchResult);

		var result = ResultDto.success(response, "auction list retrieved");

		return ResponseEntity
			.status(AUCTION_LIST_ACCESS_SUCCESS.getHttpStatus())
			.body(result);
	}
*/
}
