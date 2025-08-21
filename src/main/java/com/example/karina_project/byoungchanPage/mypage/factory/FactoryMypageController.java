package com.example.karina_project.byoungchanPage.mypage.factory;


import com.example.karina_project.byoungchanPage.mypage.factory.request.PutFactoryMyPageProfileRequest;
import com.example.karina_project.byoungchanPage.mypage.factory.response.GetFactoryMyPageProfileResponse;
import com.example.karina_project.byoungchanPage.mypage.factory.response.GetFactoryMyPageResponse;
import com.example.karina_project.sehyukPage.login_page.CustomUserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/factory")
public class FactoryMypageController {

    private final FactoryMypageService factoryMypageService;


    // 공장 마이페이지 컨트롤러
    @GetMapping("/mypage")
    public ResponseEntity<List<GetFactoryMyPageResponse>> getUserArticles(Authentication authentication) {
        return ResponseEntity.ok(factoryMypageService.getUserArticles(authentication));
    }


    @GetMapping("/mypage/profile")
    public ResponseEntity<GetFactoryMyPageProfileResponse> getUserProfileArticles(
            @AuthenticationPrincipal CustomUserDetail user) {
        return ResponseEntity.ok(factoryMypageService.getUserProfileArticles(user.getId()));
    }

    @PutMapping("/mypage/profile")
    public ResponseEntity<?> putUserProfileArticles(
            @RequestBody PutFactoryMyPageProfileRequest putFactoryMyPageProfileRequest,
            @AuthenticationPrincipal CustomUserDetail user) {

        boolean success = factoryMypageService.putUserProfileArticles(
                putFactoryMyPageProfileRequest,
                user.getId() // 로그인한 유저의 ID
        );

        return ResponseEntity.ok(Map.of("success", success));
    }

}
