package com.example.karina_project.byoungchanPage.mypage.factory;


import com.example.karina_project.byoungchanPage.mypage.factory.request.PutFactoryMyPageProfileRequest;
import com.example.karina_project.byoungchanPage.mypage.factory.response.GetFactoryMyPageProfileResponse;
import com.example.karina_project.byoungchanPage.mypage.factory.response.GetFactoryMyPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class FactoryMypageController {

    private final FactoryMypageService factoryMypageService;


    // 공장 마이페이지 컨트롤러
    @GetMapping("/factory/mypage/{userId}")
    public ResponseEntity<List<GetFactoryMyPageResponse>> getUserArticles(@PathVariable Long userId) {
        return ResponseEntity.ok(factoryMypageService.getUserArticles(userId));
    }

    @GetMapping("/factory/mypage/profile/{userId}")
    public ResponseEntity<GetFactoryMyPageProfileResponse> getUserProfileArticles(@PathVariable Long userId) {
        return ResponseEntity.ok(factoryMypageService.getUserProfileArticles(userId));
    }

    @PutMapping("/factory/mypage/profile/{userId}")
    public ResponseEntity<?> putUserProfileArticles(@RequestBody PutFactoryMyPageProfileRequest putFactoryMyPageProfileRequest
    , @PathVariable Long userId) {
        boolean success = factoryMypageService.putUserProfileArticles(putFactoryMyPageProfileRequest, userId);
        return ResponseEntity.ok().body(Map.of("success", success));
    }

}
