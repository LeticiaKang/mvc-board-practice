package com.fastcampus.boardpractice.repository;



import com.fastcampus.boardpractice.config.JpaConfig;
import com.fastcampus.boardpractice.domain.Article;
import com.fastcampus.boardpractice.domain.UserAccount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JPA 연결 테스트")
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 테스트데이터를 따로 불러오지 않고 설정되어 있는 db를 쓴다.
@Import(JpaConfig.class)
@DataJpaTest
class JpaRepositoryTest {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;
    private final UserAccountRepository userAccountRepository;

    public JpaRepositoryTest(
            @Autowired ArticleRepository articleRepository,
            @Autowired ArticleCommentRepository articleCommentRepository,
            @Autowired UserAccountRepository userAccountRepository
    ) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
        this.userAccountRepository = userAccountRepository;
    }

    @DisplayName("select 테스트")
    @Test
    void givenTestData_whenSelecting_thenWorksFine() {
        // Given

        // When
        List<Article> articles = articleRepository.findAll();
        System.out.println("==========" + articles);
        // Then
        assertThat(articles)
                .isNotNull()
                .hasSize(123);
    }

    @DisplayName("insert 테스트")
    @Test
    void givenTestData_whenInserting_thenWorksFine() {
        // Given
        long previousCount = articleRepository.count();
        UserAccount userAccount = userAccountRepository.save(UserAccount.of("uno", "pw", null, null, null));
        Article article = Article.of(userAccount, "new article", "new content", "#spring");

        // When
        articleRepository.save(article);

        // Then
        assertThat(articleRepository.count()).isEqualTo(previousCount + 1);
    }

    @DisplayName("update 테스트")
    @Test
    void givenTestData_whenUpdating_thenWorksFine() {
        // Given
        Article article = articleRepository.findById(1L).orElseThrow();    // 1번을 가져오는데, 없으면 종료(1번은 무조건 있으니까 findById(1L)사용)
        String updatedHashtag = "#springboot";  //해시태그를 변경한다
        article.setHashtag(updatedHashtag);

        // When
        Article savedArticle = articleRepository.saveAndFlush(article);
         //* save하고 flush를 하지 않으면 rollback이 되기 때문에 변경점이 없어 update가 일어나지 않는다.

        // Then
        assertThat(savedArticle).hasFieldOrPropertyWithValue("hashtag", updatedHashtag); //hashtag필드가 updatedHashtag 되었는가
    }

    @DisplayName("delete 테스트") //글이 사라질 때, 댓글도 같이 지어지는지
    @Test
    void givenTestData_whenDeleting_thenWorksFine() {
        // Given
        Article article = articleRepository.findById(1L).orElseThrow(); //1번 게시글의 댓글의 수
        long previousArticleCount = articleRepository.count(); //기존 게시글의 수
        long previousArticleCommentCount = articleCommentRepository.count(); //기존 댓글의 수
        int deletedCommentsSize = article.getArticleComments().size(); //지워진 댓글의 수

        // When
        articleRepository.delete(article);

        // Then
        assertThat(articleRepository.count()).isEqualTo(previousArticleCount - 1);
        assertThat(articleCommentRepository.count()).isEqualTo(previousArticleCommentCount - deletedCommentsSize);
    }

}