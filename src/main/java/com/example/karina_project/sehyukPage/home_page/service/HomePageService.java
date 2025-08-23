package com.example.karina_project.sehyukPage.home_page.service;

import com.example.karina_project.repository.ArticleRepository;
import com.example.karina_project.sehyukPage.home_page.dto.ArticleDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomePageService {

    private  final ArticleRepository articleRepository;

    public List<ArticleDto> getArticlesByTime() {
       Pageable pageable = PageRequest.of(0, 9);
       List<ArticleDto> articleDtos = articleRepository.findArticlesByStatusNotContainsKeywordWithPostTimeDesc("매칭 완료", pageable).stream().map(ArticleDto::from).collect(Collectors.toList());

       return articleDtos;
    }

    @Transactional
    public List<ArticleDto> getArticlesByFishSpecies(String fishSpecies) {
        Pageable pageable = (Pageable) PageRequest.of(0, 9);
        String searchSpecies = "$.\"" + fishSpecies + "\"";
        List<ArticleDto> articleDtos = articleRepository.findArticlesByStatusNotContainsKeywordWithPostTimeDesc("매칭 완료", searchSpecies, pageable).stream().map(ArticleDto::from).collect(Collectors.toList());

        return articleDtos;
    }
}
