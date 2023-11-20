package com.fastcampus.boardpractice.repository;

import com.fastcampus.boardpractice.domain.ArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long> {
}
