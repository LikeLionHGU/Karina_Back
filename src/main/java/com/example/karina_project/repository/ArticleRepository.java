package com.example.karina_project.repository;

import com.example.karina_project.domain.Article;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    // 특정 사용자 글 전체(최신순)
    List<Article> findByUserIdOrderByIdDesc(Long userId);

    // 특정 사용자 최신 글 1개
    Optional<Article> findTop1ByUserIdOrderByIdDesc(Long userId);

    @Query("SELECT a FROM Article a JOIN FETCH a.user WHERE a.status <> :status ORDER BY a.postTime DESC")
    List<Article> findArticlesByStatusNotContainsKeywordWithPostTimeDesc(@Param("status") String status, Pageable pageable);

    @Query("SELECT a FROM Article a JOIN FETCH a.user WHERE a.status <> :status AND a.fishSpecies like :fishSpecies ORDER BY a.postTime DESC")
    List<Article> findArticlesByStatusNotContainsKeywordAndContainFishSpeciesWithPostTimeDesc(
            @Param("status") String status,
            @Param("fishSpecies") String fishSpecies,
            Pageable pageable
    );
}

