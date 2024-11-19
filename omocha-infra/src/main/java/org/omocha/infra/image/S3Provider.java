package org.omocha.infra.image;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.omocha.domain.image.ImageProvider;
import org.omocha.domain.image.exception.ImageDeleteFailException;
import org.omocha.domain.image.exception.ImageUploadFailException;
import org.omocha.domain.image.exception.ImageWrongFileNameException;
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

	@Value("${cloud.aws.s3.bucket}")
	private String bucketName;

	@Value("${cloud.aws.s3.upload-path}")
	private String s3Key;

	private final AmazonS3 amazonS3;

	@Override
	public String uploadFile(MultipartFile file) {
		String fileName = createFileName(file.getOriginalFilename());
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
	public void deleteFile(String imagePath) {
		try {
			amazonS3.deleteObject(bucketName, imagePath);
			log.info("Deleted file from S3: {}", imagePath);
		} catch (AmazonServiceException e) {
			throw new ImageDeleteFailException(imagePath);
		}
	}

	private String createFileName(String fileName) { // 먼저 파일 업로드 시, 파일명을 난수화하기 위해 random으로 돌립니다.
		return UUID.randomUUID().toString().concat(getFileExtension(fileName));
	}

	private String getFileExtension(
		String fileName) { // file 형식이 잘못된 경우를 확인하기 위해 만들어진 로직이며, 파일 타입과 상관없이 업로드할 수 있게 하기 위해 .의 존재 유무만 판단하였습니다.
		try {
			return fileName.substring(fileName.lastIndexOf("."));
		} catch (StringIndexOutOfBoundsException e) {
			throw new ImageWrongFileNameException(fileName);
		}
	}
}
