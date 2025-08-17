package com.example.karina_project.sehyukPage.home_page.service;

import com.example.karina_project.repository.ArticleRepository;
import com.example.karina_project.sehyukPage.home_page.dto.ArticleDto;
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

    public List<ArticleDto> getArticlesByFishSpecies(String fish_species) {
        Pageable pageable = (Pageable) PageRequest.of(0, 9);
        String search_species = "%" + fish_species + "%";
        List<ArticleDto> articleDtos = articleRepository.findArticlesByStatusNotContainsKeywordAndContainFishSpeciesWithPostTimeDesc("매칭 완료", search_species, pageable).stream().map(ArticleDto::from).collect(Collectors.toList());

        return articleDtos;
    }
}
