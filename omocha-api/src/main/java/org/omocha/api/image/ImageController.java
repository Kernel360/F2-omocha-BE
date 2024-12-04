package org.omocha.api.image;

import static org.omocha.domain.common.code.SuccessCode.*;

import org.omocha.api.common.response.ResultDto;
import org.omocha.api.image.dto.ImageDto;
import org.omocha.api.image.dto.ImageDtoMapper;
import org.omocha.domain.image.ImageCommand;
import org.omocha.domain.image.ImageInfo;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/images")
public class ImageController implements ImageApi {

	private final ImageDtoMapper imageDtoMapper;

	private final ImageFacade imageFacade;

	@PostMapping(
		consumes = MediaType.MULTIPART_FORM_DATA_VALUE
	)
	@Override
	public ResponseEntity<ResultDto<ImageDto.ImageAddResponse>> imageAdd(
		@RequestPart(value = "image") MultipartFile image
	) {
		ImageCommand.AddImage addCommand = imageDtoMapper.toCommand(image);

		ImageInfo.AddImage addImageInfo = imageFacade.addImage(addCommand);

		ImageDto.ImageAddResponse response = imageDtoMapper.toResponse(addImageInfo);

		ResultDto<ImageDto.ImageAddResponse> result = ResultDto.res(
			IMAGE_UPLOAD_SUCCESS.getStatusCode(),
			IMAGE_UPLOAD_SUCCESS.getDescription(),
			response
		);

		return ResponseEntity
			.status(IMAGE_UPLOAD_SUCCESS.getHttpStatus())
			.body(result);
	}

	@DeleteMapping("")
	@Override
	public ResponseEntity<ResultDto<Void>> imageDelete(
		@RequestBody @Valid ImageDto.ImageDeleteRequest request
	) {
		ImageCommand.DeleteImage deleteCommand = imageDtoMapper.toCommand(request.imagePath());

		imageFacade.deleteImage(deleteCommand);

		ResultDto<Void> result = ResultDto.res(
			IMAGE_DELETE_SUCCESS.getStatusCode(),
			IMAGE_DELETE_SUCCESS.getDescription()
		);

		return ResponseEntity
			.status(IMAGE_DELETE_SUCCESS.getHttpStatus())
			.body(result);

	}
}
