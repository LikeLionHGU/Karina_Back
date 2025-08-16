package com.example.karina_project.sehyukPage.home_page.service;

import com.example.karina_project.repository.ArticleRepository;
import com.example.karina_project.sehyukPage.home_page.dto.ArticleDto;
import com.example.karina_project.sehyukPage.home_page.response.FisherResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FisherService {

    private  final ArticleRepository articleRepository;

    public List<ArticleDto> getArticlesByTime() {

       List<ArticleDto> articleDtos = articleRepository.findTop9ByOrderByPostTimeDesc().stream().map(ArticleDto::from).collect(Collectors.toList());

       return articleDtos;
    }
}
