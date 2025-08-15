package com.example.karina_project.byoungchanPage.mypage;


import com.example.karina_project.byoungchanPage.mypage.response.MypageUserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MypageController {

    private final MypageService mypageService;

    @GetMapping("/show/update/{showId}")
    public ResponseEntity<MypageUserInfoResponse> getShowMypage(@PathVariable Long userId) {
        return ResponseEntity.ok(mypageService.getShowPage(userId));
    }
}
