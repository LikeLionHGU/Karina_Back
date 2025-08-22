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

    List<Article> findByUserIdAndStatusNotOrderByIdDesc(Long userId, String status);

    List<Article> findByUserIdAndStatusOrderByIdDesc(Long userId, String status);

    @Query("SELECT a FROM Article a JOIN FETCH a.user WHERE a.status <> :status ORDER BY a.postDate DESC")
    List<Article> findArticlesByStatusNotContainsKeywordWithPostTimeDesc(@Param("status") String status, Pageable pageable);

    @Query(
            value = "SELECT a.* FROM article a " +
                    "JOIN users u ON a.user_id = u.id " +
                    "WHERE a.status <> :status " +
                    "AND JSON_CONTAINS_PATH(a.fish_info, 'one', CONCAT('$.', :fishSpecies)) " +
                    "ORDER BY a.post_date DESC",
            nativeQuery = true
    )
    List<Article> findArticlesByStatusNotContainsKeywordAndContainFishSpeciesWithPostTimeDesc(
            @Param("status") String status,
            @Param("fishSpecies") String fishSpecies,
            Pageable pageable
    );

    @Query("SELECT a FROM Article a JOIN FETCH a.user WHERE a.id = :id")
    Optional<Article> findByIdWithUser(@Param("id") Long id);
}

