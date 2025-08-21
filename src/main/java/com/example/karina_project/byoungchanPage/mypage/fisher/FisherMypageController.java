package com.example.karina_project.byoungchanPage.mypage.fisher;

import com.example.karina_project.byoungchanPage.mypage.fisher.request.PutFisherMyPageInfoRequest;
import com.example.karina_project.byoungchanPage.mypage.fisher.request.PutFisherMypageArticleRequest;
import com.example.karina_project.byoungchanPage.mypage.fisher.response.GetFisherMyPageArticleResponse;
import com.example.karina_project.byoungchanPage.mypage.fisher.response.GetFisherMyPageInfoResponse;
import com.example.karina_project.byoungchanPage.mypage.fisher.response.GetFisherMyPageResponse;
import com.example.karina_project.sehyukPage.login_page.CustomUserDetail;
import io.jsonwebtoken.security.RsaPrivateJwk;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fisher")
public class FisherMypageController {

    private final FisherMypageService fisherMypageService;

    @GetMapping("/mypage")
    public ResponseEntity<List<GetFisherMyPageResponse>> getFisherMypage(@AuthenticationPrincipal CustomUserDetail user) {
        return ResponseEntity.ok(fisherMypageService.getFisherMypageServiece(user.getId()));
    }

    @PutMapping("/mypage/{articleId}")
    public ResponseEntity<?> matchAccepting(@PathVariable Long articleId){
        boolean success = fisherMypageService.matchAccepting(articleId);
        return ResponseEntity.ok().body(Map.of("success", success));
    }

    @GetMapping("/mypage/article")
    public ResponseEntity<List<GetFisherMyPageArticleResponse>> getFisherMypageArticle(
            @AuthenticationPrincipal CustomUserDetail user) {
        return ResponseEntity.ok(fisherMypageService.getFisherMypageArticleServiece(user.getId()));
    }

    @PutMapping("/mypage/article/{articleId}")
    public ResponseEntity<?> editFisherMypageArticle(@RequestBody PutFisherMypageArticleRequest putFisherMypageArticleRequest, @PathVariable Long articleId){
        boolean success = fisherMypageService.editFisherMyPageArticleService(putFisherMypageArticleRequest, articleId);
        return ResponseEntity.ok().body(Map.of("success", success));
    }

    @GetMapping("/mypage/profile")
    public ResponseEntity<GetFisherMyPageInfoResponse> getFisherMypageInfo(
            @AuthenticationPrincipal CustomUserDetail user) {
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
