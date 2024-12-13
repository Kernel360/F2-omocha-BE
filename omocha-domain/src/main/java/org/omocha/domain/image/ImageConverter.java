package org.omocha.domain.image;

import org.springframework.web.multipart.MultipartFile;

public interface ImageConverter {

	String convertToWebp(MultipartFile file);
}
