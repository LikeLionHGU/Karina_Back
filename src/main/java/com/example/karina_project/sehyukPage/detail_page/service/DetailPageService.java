package com.example.karina_project.sehyukPage.detail_page.service;

import com.example.karina_project.domain.Article;
import com.example.karina_project.domain.Matching;
import com.example.karina_project.domain.User;
import com.example.karina_project.repository.ArticleRepository;
import com.example.karina_project.repository.MatchingRepository;
import com.example.karina_project.repository.UserRepository;
import com.example.karina_project.sehyukPage.detail_page.dto.DetailPageDto;
import com.example.karina_project.sehyukPage.detail_page.request.DetailPageRequest;
import com.example.karina_project.sehyukPage.home_page.dto.ArticleDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DetailPageService {

    private final ArticleRepository articleRepository;
    private final MatchingRepository matchingRepository;
    private final UserRepository userRepository;

    public DetailPageDto getDetailArticleInfo(Long article_id) {
        Article article = articleRepository.findByIdWithUser(article_id).orElse(null);
        DetailPageDto detailPageDto = DetailPageDto.from(article);
        return detailPageDto;
    }

    @Transactional
    public String sendMatchingRequest(DetailPageRequest request) {
        Article targetArticle = articleRepository.findByIdWithUser(request.getArticle_id()).orElse(null);
        if(targetArticle == null) {
            return "게시물이 존재하지 않습니다";
        }
        User requestUser = userRepository.findById(request.getFactory_id()).orElse(null);
        if(requestUser == null) {
            return  "요청을 보낸 유저가 존재하지 않습니다";
        }
        if(targetArticle.getStatus().equals("매칭 완료")){
            return "이미 매칭 완료된 게시물입니다";
        }

        Matching matching = matchingRepository.findByArticleIdAndFactoryId(request.getArticle_id(), request.getFactory_id());
        if(matching == null) {
            matchingRepository.save(Matching.from(targetArticle, request.getFactory_id()));
            if(targetArticle.getStatus().equals("대기 중")){
                targetArticle.setStatus("매칭 대기");
            }
        }

        return "매칭신청 완료";
    }

}
