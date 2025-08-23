package com.example.karina_project.repository;

import com.example.karina_project.domain.Matching;
import com.example.karina_project.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MatchingRepository extends JpaRepository<Matching, Long> {
    List<Matching> findByArticleIdAndFactory(Long articleId, User factory);
    List<Matching> findByArticleId(Long articleId);

    Matching find1ByArticleIdAndFactory(Long articleId, User factory);

    List<Matching> findByFactoryAndMatchingStatusOrderByIdDesc(User factory, String status);
    List<Matching> findByFactoryAndMatchingStatusNotOrderByIdDesc(User factory, String status);

    List<Matching> findByArticleIdInAndMatchingStatus(List<Long> articleIds, String status);
    List<Matching> findByArticleIdAndMatchingStatus(Long articleId, String status);




}
