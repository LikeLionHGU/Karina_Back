package com.example.karina_project.byoungchanPage.mypage.factory;


import com.example.karina_project.byoungchanPage.mypage.factory.request.FactoryMyPageRequestWithOnlyArticleId;
import com.example.karina_project.byoungchanPage.mypage.factory.request.PutFactoryMyPageProfileRequest;
import com.example.karina_project.byoungchanPage.mypage.factory.response.GetFactoryMyPageProfileResponse;
import com.example.karina_project.byoungchanPage.mypage.factory.response.GetFactoryMyPageResponse;
import com.example.karina_project.sehyukPage.login_page.CustomUserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/factory")
public class FactoryMyPageController {

    private final FactoryMyPageService factoryMypageService;

    @GetMapping("/mypage")
    public ResponseEntity<GetFactoryMyPageResponse> getUserArticles(Authentication authentication) {
        GetFactoryMyPageResponse response = factoryMypageService.getUserArticles(authentication);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/mypage/profile")
    public ResponseEntity<GetFactoryMyPageProfileResponse> getUserProfileArticles(
            @AuthenticationPrincipal(expression = "id") Long userId) {
        return ResponseEntity.ok(factoryMypageService.getUserProfileArticles(userId));
    }


    @PutMapping("/mypage/profile")
    public ResponseEntity<?> putUserProfileArticles(@RequestBody PutFactoryMyPageProfileRequest putFactoryMyPageProfileRequest, @AuthenticationPrincipal CustomUserDetail user) {
        boolean success = factoryMypageService.putUserProfileArticles(
                putFactoryMyPageProfileRequest,
                user.getId()
        );

        return ResponseEntity.ok(Map.of("success", success));
    }

    @PostMapping("/mypage/matchingCancel")
    public ResponseEntity<String> matchingCancel(@RequestBody FactoryMyPageRequestWithOnlyArticleId request, Authentication authentication) {
        String response = factoryMypageService.requestMatchingCancel(request, authentication);

        return ResponseEntity.ok().body(response);
    }
}
