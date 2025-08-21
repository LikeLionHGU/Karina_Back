package com.example.karina_project.byoungchanPage.mypage.factory;


import com.example.karina_project.byoungchanPage.mypage.factory.request.PutFactoryMyPageProfileRequest;
import com.example.karina_project.byoungchanPage.mypage.factory.response.GetFactoryMyPageProfileResponse;
import com.example.karina_project.byoungchanPage.mypage.factory.response.GetFactoryMyPageResponse;
import com.example.karina_project.domain.User;
import com.example.karina_project.repository.ArticleRepository;
import com.example.karina_project.repository.UserRepository;
import com.example.karina_project.sehyukPage.login_page.CustomUserDetail;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FactoryMypageService {

    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    //    @GetMapping("/factory/mypage")
    @Transactional(readOnly = true)
    public List<GetFactoryMyPageResponse> getUserArticles(Authentication authentication) {
        CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();
        Long userId = userDetails.getId(); // 여기서 userId 꺼냄

        return articleRepository.findByUserIdOrderByIdDesc(userId)
                .stream()
                .map(GetFactoryMyPageResponse::from)
                .toList();
    }

    //@GetMapping("/factory/mypage/profile")
    public GetFactoryMyPageProfileResponse getUserProfileArticles(Long userId) {

        return userRepository.findById(userId)
                .map(GetFactoryMyPageProfileResponse::from)
                .orElseThrow(() -> new EntityNotFoundException("해당 유저가 없습니다. id=" + userId));
    }
    //@PutMapping("/factory/mypage/profile")
    public boolean putUserProfileArticles(@RequestBody PutFactoryMyPageProfileRequest putFactoryMyPageProfileRequest, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("유저가 없습니다. id=" + userId));
        user.setLoginId(putFactoryMyPageProfileRequest.getLoginId());
        user.setName(putFactoryMyPageProfileRequest.getName());
        user.setPhoneNumber(putFactoryMyPageProfileRequest.getPhoneNumber());
        user.setMainAddress(putFactoryMyPageProfileRequest.getMainAddress());
        user.setDetailAddress(putFactoryMyPageProfileRequest.getDetailAddress());
        userRepository.save(user);
        return true;

    }
}
