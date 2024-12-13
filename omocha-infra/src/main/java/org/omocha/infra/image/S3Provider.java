package org.omocha.infra.image;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.omocha.domain.image.ImageProvider;
import org.omocha.domain.image.exception.ImageDeleteFailException;
import org.omocha.domain.image.exception.ImageUploadFailException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// TODO : exception 처리 + 코드 리펙토링 (조금 더 좋은 설계)
@Slf4j
@Component
@RequiredArgsConstructor
public class S3Provider implements ImageProvider {

	private static final String CONTENT_TYPE_WEBP = "image/webp";

	@Value("${cloud.aws.s3.bucket}")
	private String bucketName;

	@Value("${cloud.aws.s3.upload-path}")
	private String s3Key;

	private final AmazonS3 amazonS3;
	private final FileNameGenerator fileNameGenerator;

	@Override
	public String uploadFile(MultipartFile file) {
		String originalFilename = file.getOriginalFilename();
		String extension = fileNameGenerator.getFileExtension(originalFilename);
		String fileName = fileNameGenerator.generateUniqueFilename(originalFilename, extension);

		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentLength(file.getSize());
		objectMetadata.setContentType(file.getContentType());

		try (InputStream inputStream = file.getInputStream()) {
			String imagePath = s3Key + fileName;
			amazonS3.putObject(new PutObjectRequest(bucketName, imagePath, inputStream, objectMetadata)
				.withCannedAcl(CannedAccessControlList.PublicRead));
			log.info("Uploaded image to " + imagePath);
			return imagePath;
		} catch (IOException e) {
			throw new ImageUploadFailException(fileName);
		}
	}

	@Override
	public String uploadWebpFile(byte[] byteFile, String webpImagePath) {

		try {
			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.setContentLength(byteFile.length);
			objectMetadata.setContentType(CONTENT_TYPE_WEBP);

			amazonS3.putObject(
				new PutObjectRequest(bucketName, webpImagePath, new ByteArrayInputStream(byteFile), objectMetadata)
					.withCannedAcl(CannedAccessControlList.PublicRead));
			return webpImagePath;
		} catch (AmazonServiceException e) {
			throw new ImageUploadFailException(webpImagePath);
		}

	}

	@Override
	public void deleteFile(String imagePath) {
		try {
			amazonS3.deleteObject(bucketName, imagePath);
			log.info("Deleted file from S3: {}", imagePath);
		} catch (AmazonServiceException e) {
			throw new ImageDeleteFailException(imagePath);
		}
	}

}
