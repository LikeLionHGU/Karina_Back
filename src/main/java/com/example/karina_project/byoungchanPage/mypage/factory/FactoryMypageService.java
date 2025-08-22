package com.example.karina_project.byoungchanPage.mypage.factory;


import com.example.karina_project.byoungchanPage.mypage.factory.request.FactoryMyPageRequestWithOnlyArticleId;
import com.example.karina_project.byoungchanPage.mypage.factory.request.PutFactoryMyPageProfileRequest;
import com.example.karina_project.byoungchanPage.mypage.factory.response.GetFactoryMyPageProfileResponse;
import com.example.karina_project.byoungchanPage.mypage.factory.response.GetFactoryMyPageResponse;
import com.example.karina_project.domain.Article;
import com.example.karina_project.domain.Matching;
import com.example.karina_project.domain.User;
import com.example.karina_project.repository.ArticleRepository;
import com.example.karina_project.repository.MatchingRepository;
import com.example.karina_project.repository.UserRepository;
import com.example.karina_project.sehyukPage.login_page.CustomUserDetail;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FactoryMypageService {

    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final MatchingRepository matchingRepository;

    @Transactional
    public List<GetFactoryMyPageResponse> getUserArticles(Authentication authentication) {
        CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();

        List<GetFactoryMyPageResponse> response = matchingRepository.findByFactorIdOrderByIdDesc(userDetails.getUsername()).stream().map(GetFactoryMyPageResponse::from).collect(Collectors.toList());

        return response;
    }

    public GetFactoryMyPageProfileResponse getUserProfileArticles(Long userId) {

        return userRepository.findById(userId)
                .map(GetFactoryMyPageProfileResponse::from)
                .orElseThrow(() -> new EntityNotFoundException("해당 유저가 없습니다. id=" + userId));
    }

    @Transactional
    public boolean putUserProfileArticles(@RequestBody PutFactoryMyPageProfileRequest putFactoryMyPageProfileRequest, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("유저가 없습니다. id=" + userId));
        user.setName(putFactoryMyPageProfileRequest.getName());
        user.setPhoneNumber(putFactoryMyPageProfileRequest.getPhoneNumber());
        user.setMainAddress(putFactoryMyPageProfileRequest.getMainAddress());
        user.setDetailAddress(putFactoryMyPageProfileRequest.getDetailAddress());

        return true;
    }

    @Transactional
    public String requestMatchingCancel(FactoryMyPageRequestWithOnlyArticleId request, Authentication authentication) {
        CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();
        Matching requestMatching = matchingRepository.findByArticleIdAndFactoryId(request.getArticleId(), userDetails.getUsername());
        if(requestMatching == null) {
            return "fail";
        }
        matchingRepository.delete(requestMatching);

        Matching matching = matchingRepository.findByArticleId(request.getArticleId());

        if(matching == null) {
            Article requestArticle = articleRepository.findById(request.getArticleId())
                    .orElseThrow(() -> new IllegalArgumentException("No Article: " + request.getArticleId()));
            requestArticle.setStatus("대기 중");
        }

        return "success";
    }
}
