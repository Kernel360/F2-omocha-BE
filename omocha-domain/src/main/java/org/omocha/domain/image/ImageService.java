package org.omocha.domain.image;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

	String uploadFile(MultipartFile file);

	void deleteFile(String imagePath);

}
