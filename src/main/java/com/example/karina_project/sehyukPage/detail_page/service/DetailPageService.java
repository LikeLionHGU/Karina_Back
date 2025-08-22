package com.example.karina_project.sehyukPage.detail_page.service;

import com.example.karina_project.domain.Article;
import com.example.karina_project.domain.Matching;
import com.example.karina_project.domain.User;
import com.example.karina_project.repository.ArticleRepository;
import com.example.karina_project.repository.MatchingRepository;
import com.example.karina_project.repository.UserRepository;
import com.example.karina_project.sehyukPage.detail_page.dto.DetailPageDto;
import com.example.karina_project.sehyukPage.detail_page.request.DetailPageRequest;
import com.example.karina_project.sehyukPage.login_page.CustomUserDetail;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DetailPageService {

    private final ArticleRepository articleRepository;
    private final MatchingRepository matchingRepository;
    private final UserRepository userRepository;

    public DetailPageDto getDetailArticleInfo(DetailPageRequest request) {
        Article article = articleRepository.findById(request.getArticleId()).orElse(null);
        DetailPageDto detailPageDto = DetailPageDto.from(article);
        return detailPageDto;
    }

    @Transactional
    public String sendMatchingRequest(DetailPageRequest request, Authentication authentication) {
        Article targetArticle = articleRepository.findByIdWithUser(request.getArticleId()).orElse(null);
        if(targetArticle == null) {
            return "게시물이 존재하지 않습니다";
        }
        CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();
        User requestUser = userRepository.findByLoginId(userDetails.getUsername());
        if(requestUser == null) {
            return  "요청을 보낸 유저가 존재하지 않습니다";
        }
        if(targetArticle.getStatus().equals("매칭 완료")) {
            return "이미 매칭 완료된 게시물입니다";
        }

        Matching requestMatching = matchingRepository.findByArticleIdAndFactory(request.getArticleId(), requestUser);
        if(requestMatching == null) {
            String requestDate = LocalDate.now().toString();
            Matching newMatching = new Matching();

            User user = userRepository.findByLoginId(userDetails.getUsername());

            newMatching.setArticle(targetArticle);
            newMatching.setFactory(user);
            newMatching.setRequestDate(requestDate);
            newMatching.setMatchingStatus("매칭 대기");

            matchingRepository.save(newMatching);
            if(targetArticle.getStatus().equals("대기 중")){
                targetArticle.setStatus("매칭 대기");
            }
        }else{
            return "이미 매칭 신청을 한 게시물입니다.";
        }

        return "매칭신청 완료";
    }

}
