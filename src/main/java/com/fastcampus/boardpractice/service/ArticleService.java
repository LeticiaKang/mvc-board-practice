package com.fastcampus.boardpractice.service;

import com.fastcampus.boardpractice.domain.Article;
import com.fastcampus.boardpractice.domain.QArticle;
import com.fastcampus.boardpractice.domain.type.SearchType;
import com.fastcampus.boardpractice.dto.ArticleDto;
import com.fastcampus.boardpractice.dto.ArticleWithCommentsDto;
import com.fastcampus.boardpractice.repository.ArticleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor //필수 필드에 대한 생성자를 자동으로 생성해줌
@Transactional
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(SearchType searchType, String searchKeyword, Pageable pageable) { //input : 검색타입, 검색어, pageable
        // TEST : DisplayName("검색어 없이 게시글을 검색하면, 게시글 페이지를 반환한다.")
        if (searchKeyword == null || searchKeyword.isBlank()) {
            return articleRepository.findAll(pageable).map(ArticleDto::from); //ArticleDto의 entity 코드가 노출되지 않음
        }
        // TEST : DisplayName("검색어와 함께 게시글을 검색하면, 게시글 페이지를 반환한다.")
        return switch (searchType){
            case TITLE -> articleRepository.findByTitleContaining(searchKeyword, pageable).map(ArticleDto::from);
            case CONTENT -> articleRepository.findByContentContaining(searchKeyword, pageable).map(ArticleDto::from);
            case ID -> articleRepository.findByUserAccount_UserIdContaining(searchKeyword, pageable).map(ArticleDto::from);
            case NICKNAME -> articleRepository.findByUserAccount_NicknameContaining(searchKeyword, pageable).map(ArticleDto::from);
            case HASHTAG -> articleRepository.findByHashtag("#" + searchKeyword, pageable).map(ArticleDto::from);
        };
    }

    @Transactional(readOnly = true)
    public ArticleWithCommentsDto getArticle(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleWithCommentsDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - articleId: " + articleId));
    }

    public void saveArticle(ArticleDto dto) {
        // TEST : DisplayName("게시글 정보를 입력하면, 게시글을 생성한다.")
        articleRepository.save(dto.toEntity());
    }

    public void updateArticle(ArticleDto dto) {
        // TEST : DisplayName("게시글의 수정 정보를 입력하면, 게시글을 수정한다.")
        try {
            Article article = articleRepository.getReferenceById(dto.id());
            if (dto.title() != null) { article.setTitle(dto.title()); }
            if (dto.content() != null) { article.setContent(dto.content()); }
            article.setHashtag(dto.hashtag());
            // save 필요없음 : class level로 Transactional이 부여되어 있기 때문에, 메소드 단위별로 트랜잭션이 묶여 있다.
            // 그래서 트랜잭션이 끝날 때, 영속성컨텍스(Hibernate는 persistent entities)는 자동으로 변동사항을 감지하여 쿼리를 날려 업데이트 해준다.
        } catch (EntityNotFoundException e) {
            // TEST : DisplayName("없는 게시글의 수정 정보를 입력하면, 경고 로그를 찍고 아무 것도 하지 않는다.")
            log.warn("게시글 업데이트 실패. 게시글을 찾을 수 없습니다 - dto: {}", dto);
        }
    }

    public void deleteArticle(long articleId) {
        // TEST : DisplayName("게시글의 ID를 입력하면, 게시글을 삭제한다")
        articleRepository.deleteById(articleId);
    }

}
