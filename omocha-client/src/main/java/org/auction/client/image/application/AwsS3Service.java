package org.auction.client.image.application;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AwsS3Service {

	@Value("${cloud.aws.s3.bucket}")
	private String bucketName;

	@Value("${cloud.aws.s3.upload-path}")
	private String imagePath;

	private final AmazonS3 amazonS3;

	public String uploadFile(MultipartFile file) {
		String fileName = createFileName(file.getOriginalFilename());
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentLength(file.getSize());
		objectMetadata.setContentType(file.getContentType());

		try (InputStream inputStream = file.getInputStream()) {
			String s3key = imagePath + fileName;
			amazonS3.putObject(new PutObjectRequest(bucketName, s3key, inputStream, objectMetadata)
				.withCannedAcl(CannedAccessControlList.PublicRead));
			log.debug("Uploaded image to " + s3key);
			return s3key;
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");
		}
	}

	public void deleteFile(String s3Key) {
		try {
			amazonS3.deleteObject(bucketName, s3Key);
			log.info("Deleted file from S3: {}", s3Key);
		} catch (AmazonServiceException e) {
			log.error("Failed to delete file from S3: {}", s3Key, e);
			throw new RuntimeException("S3에서 파일 삭제 중 오류가 발생했습니다.", e);
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
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일(" + fileName + ") 입니다.");
		}
	}

	/*// 파일 유효성 검사
	private String getFileExtension(String fileName) {
		if (fileName.length() == 0) {
			throw new PrivateException(Code.WRONG_INPUT_IMAGE);
		}
		ArrayList<String> fileValidate = new ArrayList<>();
		fileValidate.add(".jpg");
		fileValidate.add(".jpeg");
		fileValidate.add(".png");
		fileValidate.add(".JPG");
		fileValidate.add(".JPEG");
		fileValidate.add(".PNG");
		String idxFileName = fileName.substring(fileName.lastIndexOf("."));
		if (!fileValidate.contains(idxFileName)) {
			throw new PrivateException(Code.WRONG_IMAGE_FORMAT);
		}
		return fileName.substring(fileName.lastIndexOf("."));
	}*/

}
