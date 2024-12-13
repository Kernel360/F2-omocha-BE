package org.omocha.infra.image;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.omocha.domain.image.ImageConverter;
import org.omocha.domain.image.ImageProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.sksamuel.scrimage.ImmutableImage;
import com.sksamuel.scrimage.metadata.ImageMetadata;
import com.sksamuel.scrimage.webp.WebpWriter;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ImageConverterImpl implements ImageConverter {

	private final ImageProvider imageProvider;
	private final FileNameGenerator fileNameGenerator;

	@Value("${cloud.aws.s3.upload-path}")
	private String s3Key;

	private static final String WEBP = "webp";
	private static final String AVIF = "avif";

	@Override
	public String convertToWebp(MultipartFile file) {
		try {
			String originalFilename = file.getOriginalFilename();

			String fileExtension = fileNameGenerator.getFileExtension(originalFilename).toLowerCase();
			byte[] imageBytes;

			if (WEBP.equals(fileExtension) || AVIF.equals(fileExtension)) {
				imageBytes = file.getBytes();
			} else {
				imageBytes = convertImageToWebp(file.getBytes());
			}

			String newFileName = fileNameGenerator.generateUniqueFilename(originalFilename, WEBP);
			String webpImagePath = s3Key + newFileName;

			String uploadedPath = imageProvider.uploadWebpFile(imageBytes, webpImagePath);

			return uploadedPath;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private byte[] convertImageToWebp(byte[] imageBytes) {
		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			InputStream inputStream = new ByteArrayInputStream(imageBytes);

			ImmutableImage image = ImmutableImage.loader().fromStream(inputStream);
			WebpWriter writer = WebpWriter.DEFAULT;
			ImageMetadata metadata = ImageMetadata.empty;

			writer.write(image, metadata, outputStream);

			return outputStream.toByteArray();
		} catch (IOException exception) {
			throw new RuntimeException("Failed to convert image to bytes", exception);
		}
	}

}