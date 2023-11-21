package com.fastcampus.boardpractice.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@Disabled("Spring Data REST 통합테스트는 불필요하므로 제외시킴")
@DisplayName("Data REST 테스트 - (API 테스트)")
@Transactional //db에 영향을 주기 때문에 롤백을 시켜야 해서 어노테이션을 사용함. 상대적으로 무거운 테스트라고 할 수 있다.
@AutoConfigureMockMvc
@SpringBootTest
public class DataRestTest {

    private final MockMvc mvc;


    public DataRestTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("[api] 게시글 리스트 조회")
    @Test
    void givenNothing_whenRequestingArticles_thenReturnsArticlesJsonReponse() throws Exception {
        //Given

        //What & Then
        mvc.perform(get("/api/articles"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")))
                .andDo(print());
    }

    @DisplayName("[api] 게시글 단건 조회")
    @Test
    void givenNothing_whenRequestingArticle_thenReturnsArticleJsonReponse() throws Exception {
        //Given

        //What & Then
        mvc.perform(get("/api/articles/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")))
                .andDo(print());
    }

    @DisplayName("[api] 게시글 -> 댓글 리스트 조회")
    @Test
    void givenNothing_whenRequestingArticleCommentsFromArticle_thenReturnsArticleCommentsJsonReponse() throws Exception {
        //Given

        //What & Then
        mvc.perform(get("/api/articles/1/articleComments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")))
                .andDo(print());
    }

    @DisplayName("댓글 리스트 조회")
    @Test
    void givenNothing_whenRequestingArticleComments_thenReturnsArticleCommentsJsonReponse() throws Exception {
        //Given

        //What & Then
        mvc.perform(get("/api/articleComments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")))
                .andDo(print());
    }
}
