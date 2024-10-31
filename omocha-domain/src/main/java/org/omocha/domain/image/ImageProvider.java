package org.omocha.domain.image;

import org.springframework.web.multipart.MultipartFile;

public interface ImageProvider {

	String uploadFile(MultipartFile file);

	void deleteFile(String imagePath);

}
