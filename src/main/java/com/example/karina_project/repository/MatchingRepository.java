package com.example.karina_project.repository;

import com.example.karina_project.domain.Article;
import com.example.karina_project.domain.Matching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchingRepository extends JpaRepository<Matching, Long> {
    Matching findByArticleIdAndFactoryId(Long articleId, String factoryId);
    Matching findByArticleId(Long articleId);

    List<Matching> findByFactoryIdOrderByIdDesc(String factorId);
    List<Matching> findAllByArticleIdAndFactoryIdNot(Long articleId, String factoryId);
}
