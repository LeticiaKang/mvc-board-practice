package com.fastcampus.boardpractice.repository;

import com.fastcampus.boardpractice.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}