package org.omocha.domain.image;

import org.springframework.web.multipart.MultipartFile;

public interface ImageProvider {

	String uploadFile(MultipartFile file);

	String uploadWebpFile(byte[] byteFile, String webpImagePath);

	void deleteFile(String imagePath);
}
