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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FactoryMyPageService {

    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final MatchingRepository matchingRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


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

        System.out.println(userDetails.getUsername()); // userDetails.toString() 호출



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
    public boolean putUserProfileArticles(@RequestBody PutFactoryMyPageProfileRequest request, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("유저가 없습니다. id=" + userId));
        String encodedPassword = bCryptPasswordEncoder.encode(request.getPassword());

        if(request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(encodedPassword);
        }
        if(request.getPhoneNumber() != null && !request.getPhoneNumber().isBlank()) {
            user.setPhoneNumber(request.getPhoneNumber());
        }
        if(request.getMainAddress() != null && !request.getMainAddress().isBlank()) {
            user.setMainAddress(request.getMainAddress());
        }
        if(request.getDetailAddress() != null && !request.getDetailAddress().isBlank()) {
            user.setDetailAddress(request.getDetailAddress());
        }

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

        Matching requestMatching = matchingRepository.findTopByArticleIdAndFactory(articleId, factory);



        matchingRepository.delete(requestMatching);

        List<Matching> matchingOptional = matchingRepository.findByArticleId(articleId);
        if (matchingOptional.isEmpty()) {
            Article requestArticle = articleRepository.findById(articleId)
                    .orElseThrow(() -> new IllegalArgumentException("No Article: " + articleId));
            requestArticle.setStatus("대기 중");
        }

        return "success";
    }

}
