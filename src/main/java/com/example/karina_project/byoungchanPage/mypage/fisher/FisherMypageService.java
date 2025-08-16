package com.example.karina_project.byoungchanPage.mypage.fisher;

import com.example.karina_project.byoungchanPage.mypage.factory.request.PutFactoryMyPageProfileRequest;
import com.example.karina_project.byoungchanPage.mypage.factory.response.GetFactoryMyPageProfileResponse;
import com.example.karina_project.byoungchanPage.mypage.factory.response.GetFactoryMyPageResponse;
import com.example.karina_project.byoungchanPage.mypage.fisher.request.PutFisherMyPageInfoRequest;
import com.example.karina_project.byoungchanPage.mypage.fisher.request.PutFisherMypageArticleRequest;
import com.example.karina_project.byoungchanPage.mypage.fisher.response.GetFisherMyPageArticleResponse;
import com.example.karina_project.byoungchanPage.mypage.fisher.response.GetFisherMyPageInfoResponse;
import com.example.karina_project.byoungchanPage.mypage.fisher.response.GetFisherMyPageResponse;
import com.example.karina_project.domain.Article;
import com.example.karina_project.domain.User;
import com.example.karina_project.repository.ArticleRepository;
import com.example.karina_project.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FisherMypageService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;


    //@GetMapping("/mypage/{userId}")
    @Transactional(readOnly = true) // ← 이 한 줄이 핵심!
    public List<GetFisherMyPageResponse> getFisherMypageServiece(Long userId) {
        return articleRepository.findByUserIdOrderByIdDesc(userId)
                .stream()
                .map(GetFisherMyPageResponse::from)
                .toList();



    }

    //@PutMapping("/mypage/{articleId}")
    public boolean matchAccepting(@PathVariable Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new EntityNotFoundException("해당 게시글이 없습니다.. id=" + articleId));
        article.setStatus("매칭 완료");
        articleRepository.save(article);
        return true;

    }

    //@GetMapping("/mypage/article/{userId}")
    @Transactional(readOnly = true) // ← 이 한 줄
    public List<GetFisherMyPageArticleResponse> getFisherMypageArticleServiece(Long userId) {
        return articleRepository.findByUserIdOrderByIdDesc(userId)
                .stream()
                .map(GetFisherMyPageArticleResponse::from)
                .toList();
    }




    //어떤 유저의 article인지 구별할 코드 구현해야함 (로그인 구현되면 세션 이용해서 추후 개발)
    //@PutMapping("/mypage/article/{articleId}")
    public boolean editFisherMyPageArticleService(PutFisherMypageArticleRequest putFisherMypageArticleRequest, @PathVariable Long articleId){
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new EntityNotFoundException("해당 게시글이 없습니다.. id=" + articleId));
        article.setGetDate(putFisherMypageArticleRequest.getGetDate());
        article.setGetTime(putFisherMypageArticleRequest.getGetTime());
        article.setDateLimit(putFisherMypageArticleRequest.getDateLimit());

        articleRepository.save(article);
        return true;
    }

    //@GetMapping("/mypage/profile/{userId}")
    public GetFisherMyPageInfoResponse getFisherMypageInfo(Long userId) {
        return userRepository.findById(userId)
                .map(GetFisherMyPageInfoResponse::from)
                .orElseThrow(() -> new EntityNotFoundException("유저가 없습니다. id=" + userId));
    }

    //@PutMapping("/mypage/profile/{userId}")
    public boolean editFisherMyPageInfoService(@RequestBody PutFisherMyPageInfoRequest putFisherMyPageInfoRequest, @PathVariable Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("해당 유저가 없습니다. id=" + userId));
        user.setLoginId(putFisherMyPageInfoRequest.getLoginId());
        user.setPassword(putFisherMyPageInfoRequest.getPassword());
        user.setPhoneNumber(putFisherMyPageInfoRequest.getPhoneNumber());
        user.setMainAddress(putFisherMyPageInfoRequest.getMainAddress());
        user.setDetailAddress(putFisherMyPageInfoRequest.getDetailAddress());
        userRepository.save(user);
        return true;

    }
}
