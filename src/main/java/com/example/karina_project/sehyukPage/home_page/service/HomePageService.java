package com.example.karina_project.sehyukPage.home_page.service;

import com.example.karina_project.repository.ArticleRepository;
import com.example.karina_project.sehyukPage.home_page.dto.ArticleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomePageService {

    private  final ArticleRepository articleRepository;

    @Transactional
    public List<ArticleDto> getArticlesByTime() {
       Pageable pageable = PageRequest.of(0, 9);
       List<ArticleDto> articleDtos = articleRepository.findArticlesByStatusNotContainsKeywordWithPostTimeDesc("매칭 완료", pageable).stream().map(ArticleDto::from).collect(Collectors.toList());


       return articleDtos;
    }
}
