package com.example.karina_project.sehyukPage.register_page.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.karina_project.sehyukPage.register_page.config.S3Config;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    private final S3Config s3Config;
    private final AmazonS3Client amazonS3Client;

    public String uploadFile(MultipartFile file, String dirName) throws IOException {

        //뽑아낸 이미지 파일에서 이름 및 확장자 추출
        String fileName = file.getOriginalFilename();
        String ext = fileName.substring(fileName.indexOf("."));
        String uuidFileName = dirName + UUID.randomUUID() + ext;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        s3Config.amazonS3Client().putObject(
                new PutObjectRequest(bucket, uuidFileName, file.getInputStream(), metadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead)
        );

        String s3url = s3Config.amazonS3Client().getUrl(bucket, uuidFileName).toString();

        return s3url;
    }

    public void deleteFile(String fileUrl) {
        if (fileUrl == null || fileUrl.isEmpty()) {
            log.info("삭제할 파일 URL이 없습니다.");
            return;
        }

        try {
            String objectKey = new URL(fileUrl).getPath().substring(1);
            amazonS3Client.deleteObject(bucket, objectKey);
            log.info("S3 파일 삭제 성공: {}", objectKey);

        } catch (Exception e) {
            log.error("S3 파일 삭제 실패: {}. 원인: {}", fileUrl, e.getMessage());
        }
    }
}
