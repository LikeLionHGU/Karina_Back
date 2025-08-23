package com.example.karina_project.byoungchanPage.mypage.factory;


import com.example.karina_project.byoungchanPage.mypage.factory.dto.GetFactoryMyPageDto;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FactoryMyPageService {

    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final MatchingRepository matchingRepository;

    public List<GetFactoryMyPageDto> getMatchingSuccessList(User factory) {
        List<GetFactoryMyPageDto> SuccessList = matchingRepository.findByFactoryAndMatchingStatusOrderByIdDesc(factory, "매칭 성공").stream().map(GetFactoryMyPageDto::from).collect(Collectors.toList());
        return SuccessList;
    }

    public List<GetFactoryMyPageDto> getMatchingUnSuccessList(User factory) {
        List<GetFactoryMyPageDto> UnSuccessList = matchingRepository.findByFactoryAndMatchingStatusNotOrderByIdDesc(factory, "매칭 성공").stream().map(GetFactoryMyPageDto::from).collect(Collectors.toList());
        return UnSuccessList;
    }

    @Transactional(readOnly = true)
    public GetFactoryMyPageResponse getUserArticles(Authentication authentication) {
        CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();
        User factory = userRepository.findByLoginId(userDetails.getUsername());

        return GetFactoryMyPageResponse.builder()
                .matchingSuccessList(getMatchingSuccessList(factory))
                .matchingNotSuccessList(getMatchingUnSuccessList(factory))
                .build();
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
        if (authentication == null || authentication.getPrincipal() == null) {
            return "can't find user";
        }
        CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();
        User factory = userRepository.findByLoginId(userDetails.getUsername());

        Long articleId = request.getArticleId();

        Optional<Matching> requestMatchingOptional = matchingRepository.findByArticleIdAndFactory(articleId, factory);

        if (requestMatchingOptional.isEmpty()) {
            System.out.println("[DEBUG] matching not found for articleId=" + articleId + ", factory=" + (factory != null ? factory.getLoginId() : null));
            return "fail";
        }

        Matching requestMatching = requestMatchingOptional.get();
        matchingRepository.delete(requestMatching);

        Optional<Matching> matchingOptional = matchingRepository.findByArticleId(articleId);
        if (matchingOptional.isEmpty()) {
            Article requestArticle = articleRepository.findById(articleId)
                    .orElseThrow(() -> new IllegalArgumentException("No Article: " + articleId));
            requestArticle.setStatus("대기 중");
        }

        return "success";
    }

}
