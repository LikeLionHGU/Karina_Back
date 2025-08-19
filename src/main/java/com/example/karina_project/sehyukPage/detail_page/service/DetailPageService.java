package com.example.karina_project.sehyukPage.detail_page.service;

import com.example.karina_project.domain.Article;
import com.example.karina_project.repository.ArticleRepository;
import com.example.karina_project.sehyukPage.detail_page.dto.DetailPageDto;
import com.example.karina_project.sehyukPage.detail_page.request.DetailPageRequest;
import com.example.karina_project.sehyukPage.home_page.dto.ArticleDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DetailPageService {

    private final ArticleRepository articleRepository;

    public DetailPageDto getDetailArticleInfo(DetailPageRequest request) {
        Article article = articleRepository.findByIdWithUser(request.getArticle_id()).orElse(null);
        DetailPageDto detailPageDto = DetailPageDto.from(article);
        return detailPageDto;
    }

    @Transactional
    public String sendMatchingRequest(DetailPageRequest request) {
        Article targetArticle = articleRepository.findByIdWithUser(request.getArticle_id()).orElse(null);
        if(targetArticle == null) {
            return "게시물이 존재하지 않습니다";
        }
        targetArticle.setStatus("매칭 대기");

        return "매칭 신청 완료";
    }

}
