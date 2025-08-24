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

    List<Article> findByUserIdAndStatusNotOrderByIdDesc(Long userId, String status);

    List<Article> findByUserIdAndStatusOrderByIdDesc(Long userId, String status);

    @Query("SELECT a FROM Article a WHERE a.status <> :status " +
            "AND a.getDate IS NOT NULL AND a.getDate <> '' " +
            "AND a.limitDate IS NOT NULL AND a.limitDate <> '' " +
            "ORDER BY a.postDate DESC")
    List<Article> findArticlesByStatusNotContainsKeywordWithPostDateDesc(@Param("status") String status, Pageable pageable);

    @Query("SELECT a FROM Article a JOIN FETCH a.user WHERE a.id = :id")
    Optional<Article> findByIdWithUser(@Param("id") Long id);
}

