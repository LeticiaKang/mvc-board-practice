package com.fastcampus.boardpractice.service;

import com.fastcampus.boardpractice.domain.ArticleComment;
import com.fastcampus.boardpractice.dto.ArticleCommentDto;
import com.fastcampus.boardpractice.dto.ArticleDto;
import com.fastcampus.boardpractice.dto.ArticleUpdateDto;
import com.fastcampus.boardpractice.repository.ArticleCommentRepository;
import com.fastcampus.boardpractice.repository.ArticleRepository;
import com.fastcampus.boardpractice.type.SearchType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor //필수 필드에 대한 생성자를 자동으로 생성해줌
@Transactional
@Service
public class ArticleCommentService {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    @Transactional(readOnly = true)
    public List<ArticleCommentDto> searchArticleComment(Long articleId) {
        return List.of();
    }

    public void saveArticleComment(ArticleCommentDto dto) {
    }

}
