package com.fastcampus.boardpractice.dto;

/**
 * DTO for {@link com.fastcampus.boardpractice.domain.Article}
 */
public record ArticleUpdateDto(
        String title,
        String content,
        String hashtag
) {
    public static ArticleUpdateDto of(String title, String content, String hashtag) {
        return new ArticleUpdateDto(title, content, hashtag);
    }

}