package com.example.karina_project.byoungchanPage.mypage.fisher;

import com.example.karina_project.byoungchanPage.mypage.fisher.request.FisherMyPageRequestWithOnlyArticleId;
import com.example.karina_project.byoungchanPage.mypage.fisher.request.PutFisherMyPageArticleRequest;
import com.example.karina_project.byoungchanPage.mypage.fisher.request.PutFisherMyPageInfoRequest;
import com.example.karina_project.byoungchanPage.mypage.fisher.response.GetFisherMyPageArticleResponse;
import com.example.karina_project.byoungchanPage.mypage.fisher.response.GetFisherMyPageInfoResponse;
import com.example.karina_project.byoungchanPage.mypage.fisher.response.GetFisherMyPageResponse;
import com.example.karina_project.sehyukPage.login_page.CustomUserDetail;
import com.example.karina_project.sehyukPage.register_page.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fisher")
public class FisherMyPageController {

    private final FisherMyPageService fisherMypageService;
    private final FileService fileService;

    @GetMapping("/mypage")
    public ResponseEntity<List<GetFisherMyPageResponse>> getFisherMyPage(@AuthenticationPrincipal CustomUserDetail user) {
        List<GetFisherMyPageResponse> response = fisherMypageService.getFisherMypageServiece(user.getId());

        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/mypage")
    public ResponseEntity<String> matchAccepting(@RequestBody FisherMyPageRequestWithOnlyArticleId request, Authentication authentication) {
        String response = fisherMypageService.matchAccepting(request.getArticleId(), authentication);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/mypage/posts")
    public ResponseEntity<GetFisherMyPageArticleResponse> getFisherMyPageArticle(@AuthenticationPrincipal CustomUserDetail user) {
        GetFisherMyPageArticleResponse response = fisherMypageService.getFisherMyPageArticleService(user.getId());

        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/mypage/posts")
    public ResponseEntity<String> editFisherMypageArticle(@RequestPart("info") PutFisherMyPageArticleRequest putFisherMypageArticleRequest, @RequestPart("thumbnail") MultipartFile file) throws IOException {
        String s3Url = fileService.uploadFile(file, "thumbnail/");
        String response = fisherMypageService.editFisherMyPageArticleService(putFisherMypageArticleRequest, s3Url);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/mypage/profile")
    public ResponseEntity<GetFisherMyPageInfoResponse> getFisherMyPageInfo(@AuthenticationPrincipal CustomUserDetail user) {

        return ResponseEntity.ok(fisherMypageService.getFisherMypageInfo(user.getId()));
    }

    @PutMapping("/mypage/profile")
    public ResponseEntity<?> editFisherMyPageInfo(
            @RequestBody PutFisherMyPageInfoRequest putFisherMyPageInfoRequest,
            @AuthenticationPrincipal CustomUserDetail user) {

        boolean success = fisherMypageService.editFisherMyPageInfoService(
                putFisherMyPageInfoRequest,
                user.getId()
        );

        return ResponseEntity.ok(Map.of("success", success));
    }
}
