package org.omocha.infra.image;

import java.util.UUID;

import org.omocha.domain.image.exception.ImageWrongFileNameException;
import org.springframework.stereotype.Component;

@Component
public class FileNameGenerator {

	public String generateUniqueFilename(String originalFilename, String extension) {
		String baseName = extractBaseName(originalFilename);
		String uuid = UUID.randomUUID().toString();
		return baseName + "_" + uuid + "." + extension;
	}

	private String extractBaseName(String filename) {
		return filename.substring(0, filename.lastIndexOf("."));
	}

	public String getFileExtension(String filename) {
		if (filename == null || !filename.contains(".")) {
			throw new ImageWrongFileNameException(filename);
		}
		return filename.substring(filename.lastIndexOf(".") + 1);
	}
}
