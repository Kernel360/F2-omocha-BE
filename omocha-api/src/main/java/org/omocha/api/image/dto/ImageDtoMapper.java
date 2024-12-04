package org.omocha.api.image.dto;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.omocha.domain.image.ImageCommand;
import org.omocha.domain.image.ImageInfo;
import org.springframework.web.multipart.MultipartFile;

@Mapper(
	componentModel = "spring",
	injectionStrategy = InjectionStrategy.CONSTRUCTOR,
	unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface ImageDtoMapper {
	ImageCommand.AddImage toCommand(MultipartFile image);

	ImageCommand.DeleteImage toCommand(String imagePath);

	ImageDto.ImageAddResponse toResponse(ImageInfo.AddImage addImageInfo);
}
