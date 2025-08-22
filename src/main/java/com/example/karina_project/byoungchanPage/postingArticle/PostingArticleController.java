package com.example.karina_project.byoungchanPage.postingArticle;

import com.example.karina_project.byoungchanPage.postingArticle.request.CreateArticleInfoRequest;
import com.example.karina_project.byoungchanPage.postingArticle.request.EditFishInfoRequest;
import com.example.karina_project.byoungchanPage.postingArticle.response.VideoResultResponse;
import com.example.karina_project.sehyukPage.register_page.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fisher")
public class PostingArticleController {

    private final FileService fileService;
    private final PostingArticleService postingArticleService;

    @PostMapping("/post/upload")
    public ResponseEntity<VideoResultResponse> postUpload(@RequestPart("video") MultipartFile video, Authentication authentication) throws IOException {
        String s3Url = fileService.uploadFile(video, "video/");
        VideoResultResponse result = postingArticleService.makeArticle(authentication, s3Url);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/post/edit/info")
    public ResponseEntity<VideoResultResponse> editFishInfo(@RequestBody EditFishInfoRequest request) throws IOException {
        VideoResultResponse result = postingArticleService.reanalyzeFishInfo(request);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/post/info")
    public ResponseEntity<String> postArticleInfo(@RequestPart("info") CreateArticleInfoRequest request, @RequestPart("thumbnail") MultipartFile file) throws IOException {

        String s3Url = fileService.uploadFile(file, "thumbnail/");

        try {
            postingArticleService.postArticleInfo(request, s3Url);
            return ResponseEntity.ok().body("success");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("failure");
        }

    }


}
