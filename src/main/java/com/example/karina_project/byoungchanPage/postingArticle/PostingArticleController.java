package com.example.karina_project.byoungchanPage.postingArticle;

import com.example.karina_project.byoungchanPage.postingArticle.request.CreateArticleInfoRequest;
import com.example.karina_project.byoungchanPage.postingArticle.request.EditFishInfoRequest;
import com.example.karina_project.sehyukPage.register_page.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostingArticleController {

    private final FileService fileService;
    private final PostingArticleService postingArticleService;

    @PostMapping("/upload/{userId}")
    public ResponseEntity<String> postUpload(@PathVariable Long userId, @RequestPart("file") MultipartFile file) throws IOException {
        String s3Url = fileService.uploadFile(file, "verification/");
        String message = postingArticleService.registerProcess(userId, s3Url);
        return ResponseEntity.ok(message);
    }

    @PutMapping("/post/{articleId}")
    public ResponseEntity<String> editFishInfo(@PathVariable Long articleId, @RequestBody EditFishInfoRequest request) {
        String result = postingArticleService.editFishInfo(articleId, request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/post/{userId}")
    public ResponseEntity<String> postArticleInfo(@PathVariable Long userId, @RequestBody CreateArticleInfoRequest request, @RequestPart MultipartFile file) throws IOException {

        String s3Url = fileService.uploadFile(file, "fishVideo/");

        try {
            postingArticleService.postArticleInfo(userId, request, s3Url);
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("failure");
        }


    }
}
