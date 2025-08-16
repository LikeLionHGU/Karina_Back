package com.example.karina_project.byoungchanPage.mypage.fisher;

import com.example.karina_project.byoungchanPage.mypage.fisher.request.PutFisherMyPageInfoRequest;
import com.example.karina_project.byoungchanPage.mypage.fisher.request.PutFisherMypageArticleRequest;
import com.example.karina_project.byoungchanPage.mypage.fisher.response.GetFisherMyPageArticleResponse;
import com.example.karina_project.byoungchanPage.mypage.fisher.response.GetFisherMyPageInfoResponse;
import com.example.karina_project.byoungchanPage.mypage.fisher.response.GetFisherMyPageResponse;
import io.jsonwebtoken.security.RsaPrivateJwk;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class FisherMypageController {

    private final FisherMypageService fisherMypageService;

    @GetMapping("/mypage/{userId}")
    public ResponseEntity<List<GetFisherMyPageResponse>> getFisherMypage(@PathVariable Long userId){
        return ResponseEntity.ok(fisherMypageService.getFisherMypageServiece(userId));
    }

    @PutMapping("/mypage/{articleId}")
    public ResponseEntity<?> matchAccepting(@PathVariable Long articleId){
        boolean success = fisherMypageService.matchAccepting(articleId);
        return ResponseEntity.ok().body(Map.of("success", success));
    }

    @GetMapping("/mypage/article/{userId}")
    public ResponseEntity<List<GetFisherMyPageArticleResponse>> getfisherMypageArticle(@PathVariable Long userId){
        return ResponseEntity.ok(fisherMypageService.getFisherMypageArticleServiece(userId));
    }

    @PutMapping("/mypage/article/{articleId}")
    public ResponseEntity<?> editFisherMypageArticle(@RequestBody PutFisherMypageArticleRequest putFisherMypageArticleRequest, @PathVariable Long articleId){
        boolean success = fisherMypageService.editFisherMyPageArticleService(putFisherMypageArticleRequest, articleId);
        return ResponseEntity.ok().body(Map.of("success", success));
    }

    @GetMapping("/mypage/profile/{userId}")
    public ResponseEntity<GetFisherMyPageInfoResponse> getFisherMypageInfo(@PathVariable Long userId){
        return ResponseEntity.ok(fisherMypageService.getFisherMypageInfo(userId));
    }

    @PutMapping("/mypage/profile/{userId}")
    public ResponseEntity<?> EditFisherMyPageInfo(@RequestBody PutFisherMyPageInfoRequest putFisherMyPageInfoRequest, @PathVariable Long userId){
        boolean success = fisherMypageService.editFisherMyPageInfoService(putFisherMyPageInfoRequest, userId);
        return ResponseEntity.ok().body(Map.of("success", success));
    }
}
