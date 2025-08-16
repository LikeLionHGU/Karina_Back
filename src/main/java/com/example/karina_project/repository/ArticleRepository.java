package com.example.karina_project.repository;

import com.example.karina_project.domain.Article;
import com.example.karina_project.sehyukPage.home_page.dto.ArticleDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    // 특정 사용자 글 전체(최신순)
    List<Article> findByUserIdOrderByIdDesc(Long userId);

    // 특정 사용자 최신 글 1개
    Optional<Article> findTop1ByUserIdOrderByIdDesc(Long userId);

    List<Article> findTop9ByOrderByPostTimeDesc();
}
